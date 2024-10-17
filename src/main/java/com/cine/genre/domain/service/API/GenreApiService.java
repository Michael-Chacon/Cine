package com.cine.genre.domain.service.API;

import com.cine.genre.DTO.GenreResponse;
import com.cine.utils.ConfigTMDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.cine.utils.Constants.URL_BASE;

@Service
public class GenreApiService implements IApiGenre {
  @Autowired RestTemplate restTemplate;
  @Autowired ConfigTMDB token;

  @Override
  public ResponseEntity<GenreResponse> getAll() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(token.getToken());
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
