package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.ResponseSearchMovie;
import com.cine.movie.DTO.SearchMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.cine.utils.Constants.TOKEN;
import static com.cine.utils.Constants.URL_BASE;

@Service
public class ApiSearchMovieService implements IApiSearchMovie {
  @Autowired private RestTemplate restTemplate;

  @Override
  public List<SearchMovie> searchMovie(String name) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(TOKEN);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<ResponseSearchMovie> movies =
        restTemplate.exchange(
            URL_BASE + "/search/movie?query=" + name,
            HttpMethod.GET,
            entity,
            ResponseSearchMovie.class);

    ResponseSearchMovie result = movies.getBody();
    if (result != null && !result.results().isEmpty()) {
      return result.results().stream()
          .map(
              movie ->
                  new SearchMovie(
                      movie.id(),
                      movie.title(),
                      movie.vote_average(),
                      movie.release_date(),
                      movie.poster_path() != null
                          ? "https://image.tmdb.org/t/p/w500" + movie.poster_path()
                          : null))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
