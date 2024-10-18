package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.CastingDTO;
import com.cine.movie.DTO.ResponseCasting;
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
public class ApiCastingService implements IApiCasting {
  @Autowired private RestTemplate restTemplate;
  @Autowired private ConfigTMDB token;

  @Override
  public List<CastingDTO> getFullCast(Long movieId) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<ResponseCasting> allCast =
        restTemplate.exchange(
            URL_BASE + "/movie/" + movieId + "/credits",
            HttpMethod.GET,
            entity,
            ResponseCasting.class);

    ResponseCasting casting = allCast.getBody();

    return casting.cast().stream()
        .map(actor -> new CastingDTO(actor.id()))
        .collect(Collectors.toList());
  }
}
