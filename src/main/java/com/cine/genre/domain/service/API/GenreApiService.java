package com.cine.genre.domain.service.API;

import com.cine.genre.DTO.GenreResponse;
import com.cine.genre.persistence.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.cine.utils.Constants.TOKEN;
import static com.cine.utils.Constants.URL_BASE;

@Service
public class GenreApiService implements IApiGenre {
  @Autowired RestTemplate restTemplate;

  @Override
  public ResponseEntity<GenreResponse> getAll() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(TOKEN);
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
    ResponseEntity<GenreResponse> result =
        restTemplate.exchange(
            URL_BASE + "/genre/movie/list?language=es",
            HttpMethod.GET,
            entity,
           GenreResponse.class);
    return result;
  }
}
