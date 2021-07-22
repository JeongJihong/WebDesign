package com.web.curation.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //헤더에서 Jwt 받아옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) servletRequest);
        //유효 토큰인지 확인
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰이 만약 유효? 유저 정보 받아옴
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            //securityContext 에 Authentication 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
