package com.restaurant.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingFilter implements Filter {
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String sessionId = RequestContextHolder.getRequestAttributes().getSessionId();
    MDC.put("sessionId", sessionId);
    try {
      filterChain.doFilter(servletRequest, servletResponse);
    } finally {
      MDC.clear();
    }

  }
}
