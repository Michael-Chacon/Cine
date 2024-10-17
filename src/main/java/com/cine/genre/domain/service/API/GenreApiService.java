package com.cine.genre.domain.service.API;

import com.cine.genre.DTO.GenreDTO;
import com.cine.genre.DTO.GenreResponse;
import com.cine.genre.persistence.Genre;
import com.cine.utils.ConfigTMDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static com.cine.utils.Constants.URL_BASE;

@Service
public class GenreApiService implements IApiGenre {
  @Autowired RestTemplate restTemplate;
  @Autowired ConfigTMDB token;

  @Override
  public List<GenreDTO> getAllGenres() {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(httpHeaders);

    ResponseEntity<GenreResponse> result =
        restTemplate.exchange(
            URL_BASE + "/genre/movie/list?language=es",
            HttpMethod.GET,
            entity,
            GenreResponse.class);

    GenreResponse response = result.getBody();

    return response.genres().stream()
        .map(genre -> new GenreDTO(genre.id(), genre.name()))
        .collect(Collectors.toList());
  }
}
