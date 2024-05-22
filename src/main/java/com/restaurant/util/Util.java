package com.restaurant.util;

public class Util {
  public static String removeSpaces(String str) {
    return str.replaceAll("\\s+", " ").trim();
  }

}
