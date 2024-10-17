package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.DetailsMovie;
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
public class ApiDetailsMovieService implements IApiDetailsMovie {
  @Autowired private RestTemplate restTemplate;
  @Autowired ConfigTMDB token;

  @Override
  public DetailsMovie getMovie(Long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<DetailsMovie> movie =
        restTemplate.exchange(
            URL_BASE + "/movie/" + id, HttpMethod.GET, entity, DetailsMovie.class);
    return movie.getBody();
  }
}
