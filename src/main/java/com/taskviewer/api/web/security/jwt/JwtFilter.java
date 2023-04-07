package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final JwtService jwtService;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        try {
            JwtToken token = getTokenFromRequest((HttpServletRequest) req);
            if (token != null) {
                if (!jwtService.isTokenType(token, JwtToken.TokenType.ACCESS)) {
                    throw new JwtException("invalid token");
                }
                if (jwtService.isTokenExpired(token)) {
                    throw new JwtException("token expired");
                }
                Authentication auth = jwtService.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(req, res);
        } catch (JwtException ignored) {
        }
    }

    public JwtToken getTokenFromRequest(HttpServletRequest req) {
        final String bearer = req.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            String token = bearer.substring(7);
            return new JwtToken(token);
        }
        return null;
    }
}
