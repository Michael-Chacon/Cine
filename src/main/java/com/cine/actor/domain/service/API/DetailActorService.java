package com.cine.actor.domain.service.API;

import com.cine.actor.DTO.DetailActor;
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
public class DetailActorService implements IApiDetailActor {
  @Autowired private RestTemplate restTemplate;
  @Autowired ConfigTMDB token;

  @Override
  public DetailActor actor(Long id) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<DetailActor> details =
        restTemplate.exchange(
            URL_BASE + "/person/" + id, HttpMethod.GET, entity, DetailActor.class);
    return details.getBody();
  }
}
