package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.postlike.PostLikeRepository;
import org.greenbyme.angelhack.domain.posttag.PostTagRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.personalmission.PersonalMissionByUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.greenbyme.angelhack.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PersonalMissionRepository personalMissionRepository;
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MailService mailService;
    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public String saveUser(UserSaveRequestDto requestDto) throws MessagingException {
        if (isPresentEmail(requestDto.getEmail())) {
            throw new UserException(ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }

        if (isPresentNickname(requestDto.getNickname())) {
            throw new UserException(ErrorCode.MEMBER_DUPLICATED_NICKNAME);
        }

        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);
        String key = UUID.randomUUID().toString();
        saveCertificateToken(key, user);
        mailService.sendSingUpMail(user, key);
        return "OK";
    }

    @Async
    public void saveCertificateToken(String key, User user) {
        ValueOperations<String, Object> token = redisTemplate.opsForValue();
        token.set(key, user.getId(), 1, TimeUnit.HOURS);
    }

    @Transactional
    public String saveSocialUser(SocialUserSaveRequestDto requestDto) {
        if (isPresentEmail(requestDto.getEmail())) {
            throw new UserException(ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }

        if (isPresentNickname(requestDto.getNickname())) {
            throw new UserException(ErrorCode.MEMBER_DUPLICATED_NICKNAME);
        }

        if (isPresentPlatformId(requestDto.getPlatformId())) {
            throw new UserException(ErrorCode.ALREADY_SIGNUP_PLATFORMID);
        }

        User user = userRepository.save(requestDto.toEntity());
        return createToken(user);
    }

    public UserDetailResponseDto getUserDetail(Long userId) {
        User user = getUser(userId);
        List<Post> posts = postRepository.findAllByUserId(userId);
        return new UserDetailResponseDto(user, posts);
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s: 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    private User getUser(final Long userId) {
        return userRepository.findByIdFetch(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }

    public UserExpectTreeCo2ResponseDto getUserExpectTreeCo2(Long userId) {
        User user = getUser(userId);
        long missionProgressRates = 0L;
        long progressMissions = user.getPersonalMissionList().stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();

        long missionProgressedCount = postRepository.findAllByUser(user).stream()
                .filter(p -> p.getCreatedDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                .count();

        if (progressMissions == 0 || missionProgressedCount == 0) {
            missionProgressRates = 0L;
        } else {
            missionProgressRates = (long) ((double) (missionProgressedCount / progressMissions) * 100);
        }

        return new UserExpectTreeCo2ResponseDto(user, progressMissions, missionProgressRates);
    }

    public Page<PersonalMissionByUserDto> getPersonalMissionList(Long userId, Pageable pageable) {
        return personalMissionRepository.findAllByUserIdPageable(userId, pageable)
                .map(PersonalMissionByUserDto::new);
    }

    public Page<PostDetailResponseDto> getPostList(Long userId, Pageable pageable) {
        User user = getUser(userId);
        return postRepository.findAllByUser(user, pageable)
                .map(p -> new PostDetailResponseDto(p, true));
    }

    @Transactional
    public UserResponseDto updateProfile(Long userId, MultipartFile file, UserUpdateNicktDto dto) {
        User user = getUser(userId);

        if (file != null) {
            String fileName = service.storeFile(file);
            String filedUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/users/images/")
                    .path(fileName)
                    .toUriString();
            user.changePhoto(filedUrl);
        }

        user.changeNickName(dto.getNickName());
        return new UserResponseDto(user.getId());
    }

    public String login(UserLoginRequestDto dto) {
        User user = getUser(dto.getEmail());
        validateLeftUser(user);
        String encodePassword = user.getPassword();
        String rawPassword = dto.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        if (!user.isCertificated()) {
            throw new UserException("이메일 인증을 해주세요.", ErrorCode.MAIL_CERTIFICATION);
        }
        return createToken(user);
    }

    public String socialLogin(SocialUserLoginRequestDto socialUserLoginRequestDto) {
        User user = userRepository.findByPlatformId(socialUserLoginRequestDto.getPlatformId())
                .orElseThrow(() -> new UserException("등록되지 않은 소셜 유저입니다", ErrorCode.UNSIGNED_SOCIAL));
        validateLeftUser(user);
        return createToken(user);
    }

    @Transactional
    public Boolean makeDisableUser(Long userId) throws Exception {
        User user = getUser(userId);
        user.makeDisable();
        return true;
    }

    public String refreshToken(Authentication authentication) throws Exception {
        return jwtTokenProvider.makeReToken(authentication);
    }

    public Boolean isPresentEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public Boolean isPresentNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public Boolean isPresentPlatformId(String platformId) {
        return userRepository.findByPlatformId(platformId).isPresent();
    }

    private void validateLeftUser(User user) {
        if (user.isLeft() == true) {
            throw new UserException(ErrorCode.LEFT_USER);
        }
    }

    private String createToken(User user) {
        return jwtTokenProvider.createToken(user.getId(), user.getRoles());
    }

    @Transactional
    public String certifiacte(String token) {
        int userId = (int) redisTemplate.opsForValue().get(token);
        User user = getUser((long) userId);
//        user.certified();
        redisTemplate.delete(token);
        return "OK";
    }
}
