package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.personalmission.PersonalMission;
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
import java.util.stream.Collectors;

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
    public UserResponseDto saveUser(UserSaveRequestDto requestDto) {
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
        return new UserResponseDto(user.getId());
    }

    public UserDetailResponseDto getUserDetail(Long userId) {
        User user = getUser(userId);
        List<Post> posts = postRepository.findAllByUser(user);
        return new UserDetailResponseDto(user, posts);
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s: 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    private User getUser(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
    }

    public UserExpectTreeCo2ResponseDto getUserExpectTreeCo2(Long userId) {
        User user = getUser(userId);
        long missionProgressRates = 0L;
        List<PersonalMission> res = personalMissionRepository.findAllByUser(user);
        long missionCount = res.stream()
                .filter(m -> m.getPersonalMissionStatus().equals(PersonalMissionStatus.IN_PROGRESS))
                .count();
        long missionProgressCount = postRepository.findAllByUser(user).stream()
                .filter(p -> p.getCreatedDate().getDayOfYear() == LocalDateTime.now().getDayOfYear())
                .count();

        if (missionProgressCount == 0) {
            missionProgressRates = 0L;
        }
        if (missionCount == 0) {
            missionCount = 0;
        } else {
            missionProgressRates = (long) ((double) (missionProgressCount / missionCount) * 100);
        }

        return new UserExpectTreeCo2ResponseDto(user, missionCount, missionProgressRates);
    }

    public Page<PersonalMissionByUserDto> getPersonalMissionList(Long userId, Pageable pageable) {
        User user = getUser(userId);
        return personalMissionRepository.findAllByUser(user, pageable)
                .map(PersonalMissionByUserDto::new);
    }

    public Page<PostDetailResponseDto> getPostList(Long userId, Pageable pageable) {
        User user = getUser(userId);
        return postRepository.findAllByUser(user, pageable)
                .map(PostDetailResponseDto::new);
    }

    @Transactional
    public UserResponseDto updateNickName(UserUpdateNicktDto dto, Long userId) {
        User user = getUser(userId);
        user.changeNickName(dto.getNickName());
        return new UserResponseDto(user.getId());
    }

    @Transactional
    public UserResponseDto updatePhotos(Long userId, MultipartFile file) {
        User user = getUser(userId);
        String fileName = service.storeFile(file);
        String filedUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/users/images/")
                .path(fileName)
                .toUriString();

        user.changePhoto(filedUrl);
        return new UserResponseDto(user.getId());
    }

    public String login(UserLoginRequestDto dto) {
        User user = getUser(dto.getEmail());
        String encodePassword = user.getPassword();
        String rawPassword = dto.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        return jwtTokenProvider.createToken(user.getId(), user.getRoles());
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
}
