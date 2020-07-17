package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.service.dto.missionInfo.MissionInfobyUserDto;
import org.greenbyme.angelhack.service.dto.user.UserDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserLoginRequestDto;
import org.greenbyme.angelhack.service.dto.user.UserResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserSaveRequestDto;
import org.greenbyme.angelhack.util.JwtTokenProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto saveUser(UserSaveRequestDto requestDto) {
        if( userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다",ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        User user = requestDto.toEntity();
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }

    public UserDetailResponseDto getUserDetail(Long userId) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new NoResultException("없는 정보입니다"));
        return new UserDetailResponseDto(user);
    }

    @Transactional
    public String createToken(UserLoginRequestDto dto) {
        String email = dto.getEmail();
        User user = getUser(email);
        if (!user.checkPassword(dto.getPassword())) {
            throw new UserException("틀린 암호입니다", ErrorCode.WRONG_PASSWORD);
        }
        return jwtTokenProvider.createToken(dto.getEmail());
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s: 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    public Long getUserId(String email) {
        return getUser(email).getId();
    }

    public List<MissionInfobyUserDto> getMissionInfoList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED_USER));
        return user.getMissionInfoList().stream()
                .map(MissionInfobyUserDto::new)
                .collect(Collectors.toList());

    }
}
