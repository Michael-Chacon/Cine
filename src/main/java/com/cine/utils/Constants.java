package com.cine.utils;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
  public static final String URL_BASE = "https://api.themoviedb.org/3";
  @Value("${tmdb.api.token}")
  public static String TOKEN;
}
