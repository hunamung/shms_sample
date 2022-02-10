package com.skt.shms.shmsauthservice.config.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    // request 로 들어오는 Jwt 의 유효성을 검증 - JwtProvider.validationToken() 을 필터로서 FilterChain 에 추가
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        // request 에서 token 을 취한다.
        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        // 검증
        //log.info("[Verifying token]");
        //log.info(((HttpServletRequest) request).getRequestURL().toString());
        log.info("[Verifying token] >>>> " + ((HttpServletRequest) request).getRequestURL().toString());
        
        if (token != null && jwtProvider.validationToken(token)) {
            //유효한 토큰일 때 별도 처리
        }

        filterChain.doFilter(request, response);
    }
}
