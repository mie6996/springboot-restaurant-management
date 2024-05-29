package com.restaurant.constant;

import lombok.extern.slf4j.Slf4j;

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
  public static final String AUTH_ENDPOINT = "/api/v1/auth/**";
  public static final String CACHE_NAME_MENUS = "menus";

}
