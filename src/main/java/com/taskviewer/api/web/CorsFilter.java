package com.taskviewer.api.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(final HttpServletRequest request,
                                  final HttpServletResponse response,
                                  final FilterChain chain)
    throws ServletException, IOException {
    if (request.getHeader("Origin") != null) {
      response.setHeader("Access-Control-Allow-Origin",
        request.getHeader("Origin")
      );
    } else {
      response.setHeader("Access-Control-Allow-Origin",
        "*"
      );
    }
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods",
      "POST, GET, PUT, UPDATE, OPTIONS, DELETE, PATCH");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
      "Origin, X-Requested-With, Content-Type, Accept, Accept-Language, Authorization");
    if (!"OPTIONS".equals(request.getMethod())) {
      chain.doFilter(request, response);
    }
  }
}
