package com.cine.director.domain.service.API;

import com.cine.director.DTO.ApiResponseDirector;
import com.cine.director.DTO.DirectorDetailDTO;
import com.cine.director.DTO.DirectorIdDTO;
import com.cine.utils.ConfigTMDB;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.cine.utils.Constants.URL_BASE;

@Service
public class ApiDirectorService implements IApiDirector {
  private final RestTemplate restTemplate;
  private final ConfigTMDB token;

  public ApiDirectorService(RestTemplate restTemplate, ConfigTMDB token) {
    this.restTemplate = restTemplate;
    this.token = token;
  }

  @Override
  public Long findIdDirector(Long idMovie) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(headers);

    ResponseEntity<ApiResponseDirector> response =
        restTemplate.exchange(
            URL_BASE + "/movie/" + idMovie + "/credits",
            HttpMethod.GET,
            entity,
            ApiResponseDirector.class);

    ApiResponseDirector responseBody = response.getBody();
    assert responseBody != null;
    return responseBody.crew().stream()
        .filter(crew -> crew.job().equals("Director"))
        .map(director -> new DirectorIdDTO(director.id(), director.job()))
        .findFirst()
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "No se encontró al director dentro de la info de la peli."))
        .id();
  }

  public DirectorDetailDTO findDirectorById(Long idDirector) {
    HttpHeaders header = new HttpHeaders();
    header.setBearerAuth(token.getToken());
    HttpEntity<?> entity = new HttpEntity<>(header);

    ResponseEntity<DirectorDetailDTO> response =
        restTemplate.exchange(
            URL_BASE + "/person/" + idDirector, HttpMethod.GET, entity, DirectorDetailDTO.class);
    return response.getBody();
  }
}
