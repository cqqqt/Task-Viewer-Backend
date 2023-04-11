package com.taskviewer.api.web.security.jwt;

import com.taskviewer.api.model.jwt.JwtToken;
import com.taskviewer.api.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws ServletException, IOException {
        try {
            JwtToken token = getTokenFromRequest((HttpServletRequest) request);
            if (token != null) {
                if (!jwtService.isTokenType(token, JwtToken.TokenType.ACCESS)) {
                    throw new JwtException("Invalid token");
                }
                if (jwtService.isTokenExpired(token)) {
                    throw new JwtException("Token expired");
                }
                Authentication auth = jwtService.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
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
