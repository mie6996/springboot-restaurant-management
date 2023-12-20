package com.restaurant.util;

public class Utils {
  public static String removeSpaces(String str) {
    return str.replaceAll("\\s+", " ").trim();
  }

}
