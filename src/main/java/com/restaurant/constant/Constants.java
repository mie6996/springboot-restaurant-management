package com.restaurant.constant;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;

/**
 * Constants
 */
@Slf4j
public class Constants {
  public static final String OFFSET_DEFAULT = "0";
  public static final String LIMIT_DEFAULT = "5";
  public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String BASIC_TOKEN_PREFIX = "Basic ";

    public static boolean isPreflightRequest(HttpServletRequest request) {
        return HttpMethod.OPTIONS.toString().equalsIgnoreCase(request.getMethod());
    }

    public static String getAuthorizationToken(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public static boolean validateToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            log.error("Authentication Token is missing");
            return false;
        }

        if (accessToken.startsWith(BASIC_TOKEN_PREFIX)) {
            log.info("User logged with basic authentication");
            return false;
        }

        if (!accessToken.startsWith(BEARER)) {
            log.error("Authentication token is not bearer");
            return false;
        }
        return true;
    }

}
