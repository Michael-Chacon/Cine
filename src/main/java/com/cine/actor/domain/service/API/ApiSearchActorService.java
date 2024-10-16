package com.cine.actor.domain.service.API;

import com.cine.actor.DTO.ResponseSearchActor;
import com.cine.actor.DTO.SearchActor;
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
public class ApiSearchActorService implements IApiSearchActor {
  @Autowired RestTemplate restTemplate;

  @Override
  public List<SearchActor> SearchActor(String name) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(TOKEN);

    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<ResponseSearchActor> actors =
        restTemplate.exchange(
            URL_BASE + "/search/person?query=" + name,
            HttpMethod.GET,
            entity,
            ResponseSearchActor.class);

    ResponseSearchActor response = actors.getBody();
    if (response != null && response.results() != null) {
      return response.results().stream()
          .map(
              actor ->
                  new SearchActor(
                      actor.id(),
                      actor.name(),
                      actor.profile_path() != null
                          ? "https://image.tmdb.org/t/p/w500" + actor.profile_path()
                          : null))
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }
}
