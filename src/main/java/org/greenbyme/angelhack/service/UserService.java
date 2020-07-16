package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.domain.user.User;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.service.dto.user.UserDetailResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserResponseDto;
import org.greenbyme.angelhack.service.dto.user.UserSaveRequestDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto saveUser(UserSaveRequestDto requestDto) {
        User user = requestDto.toEntity();
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }

    public UserDetailResponseDto getUserDetail(Long userId) {
        User user = userRepository.findById(userId).get();
        UserDetailResponseDto responseDto = new UserDetailResponseDto(user);
        return responseDto;
    }
}
