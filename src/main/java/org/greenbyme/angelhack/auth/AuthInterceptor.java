package org.greenbyme.angelhack.auth;

import lombok.extern.slf4j.Slf4j;
import org.greenbyme.angelhack.exception.ErrorCode;
import org.greenbyme.angelhack.exception.UserException;
import org.greenbyme.angelhack.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws UserException {
        final String token = request.getHeader("jwt");
        log.info("preHandle: " + token);
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UserException("권한이 없습니다", ErrorCode.INVALID_ACCESS);
        }
        request.setAttribute("userID", jwtTokenProvider.getId(token));
        return true;
    }
}
