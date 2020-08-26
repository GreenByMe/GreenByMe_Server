package org.greenbyme.angelhack.auth.jwt;

import lombok.extern.slf4j.Slf4j;
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
            throws Exception {
        final String token = request.getHeader("jwt");
        log.info("preHandle:" + token);
        if (jwtTokenProvider.validateToken(token)) {
            throw new IllegalAccessException("권한이 없습니다");
        }
        request.setAttribute("userID", jwtTokenProvider.getId(token));
        return true;
    }
}
