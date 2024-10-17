package com.cine.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigTMDB {
  @Value("${tmdb.api.token}")
  private String token;

  public String getToken() {
    return token;
  }
}
