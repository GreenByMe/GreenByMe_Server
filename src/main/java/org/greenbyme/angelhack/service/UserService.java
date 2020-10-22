package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionRepository;
import org.greenbyme.angelhack.domain.personalmission.PersonalMissionStatus;
import org.greenbyme.angelhack.domain.post.Post;
import org.greenbyme.angelhack.domain.post.PostRepository;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PersonalMissionRepository personalMissionRepository;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private FileUploadDownloadService service;

    @Transactional
    public String saveUser(UserSaveRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }

        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new UserException("이미 가입된 닉네임 입니다.", ErrorCode.MEMBER_DUPLICATED_NICKNAME);
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);
        return createToken(user);
    }

    @Transactional
    public String saveSocialUser(SocialUserSaveRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }

        if (userRepository.findByNickname(requestDto.getNickname()).isPresent()) {
            throw new UserException("이미 가입된 닉네임 입니다.", ErrorCode.MEMBER_DUPLICATED_NICKNAME);
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

        if(file != null) {
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
        String encodePassword = user.getPassword();
        String rawPassword = dto.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        return createToken(user);
    }

    public String refreshToken(Authentication authentication) throws Exception {
        return jwtTokenProvider.makeReToken(authentication);
    }

    public Boolean checkEmail(String email) {
        return !userRepository.findByEmail(email).isPresent();
    }

    public Boolean checkNickName(String nickname) {
        return !userRepository.findByNickname(nickname).isPresent();
    }

    private String createToken(User user) {
        return jwtTokenProvider.createToken(user.getId(), user.getRoles());
    }
}
