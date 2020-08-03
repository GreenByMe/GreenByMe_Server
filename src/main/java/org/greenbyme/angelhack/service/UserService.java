package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfo;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoRepository;
import org.greenbyme.angelhack.domain.missionInfo.MissionInfoStatus;
import org.greenbyme.angelhack.domain.post.PostRepository;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfobyUserDto;
import org.greenbyme.angelhack.service.dto.post.PostDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.*;
import org.greenbyme.angelhack.util.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MissionInfoRepository missionInfoRepository;
    private final PostRepository postRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto saveUser(UserSaveRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        User user = requestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }

    public UserDetailResponseDto getUserDetail(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NoResultException("없는 정보입니다"));
        return new UserDetailResponseDto(user);
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s: 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    public UserExpectTreeCo2ResponseDto getUserExpectTreeCo2(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoResultException("등록된 사용자가 없습니다."));
        long missionProgressRates = 0L;
        List<MissionInfo> res = missionInfoRepository.findAllByUser(user);
        long missionCount = res.stream()
                .filter(m -> m.getMissionInfoStatus().equals(MissionInfoStatus.IN_PROGRESS))
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
            missionProgressRates = (long)( (double)(missionProgressCount /missionCount) * 100);
        }

        return new UserExpectTreeCo2ResponseDto(user, missionCount, missionProgressRates);
    }

    public List<MissionInfobyUserDto> getMissionInfoList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return user.getMissionInfoList().stream()
                .map(MissionInfobyUserDto::new)
                .collect(Collectors.toList());
    }

    public List<PostDetailResponseDto> getPostList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return user.getPostList().stream()
                .map(PostDetailResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto updateNickName(UserUpdateNicktDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->new UserException(ErrorCode.UNSIGNED_USER));
        user.changeNickName(dto.getNickName());
        return new UserResponseDto(user.getId());
    }

    public UserResponseDto updatePhotos(UserUpdatePhotoDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(()->new UserException(ErrorCode.UNSIGNED_USER));
        user.changePhoto(dto.getPhotoUrl());
        return new UserResponseDto(user.getId());
    }

    public User login(UserLoginRequestDto dto) {
        User user = getUser(dto.getEmail());
        String encodePassword = user.getPassword();
        String rawPassword = dto.getPassword();
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        return user;
    }
}
