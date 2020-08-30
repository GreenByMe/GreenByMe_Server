package org.greenbyme.angelhack.service;

import lombok.RequiredArgsConstructor;
import org.greenbyme.angelhack.domain.user.UserRepository;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String id) {
        return userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.INVALID_ACCESS.getMessage()));
    }
}
