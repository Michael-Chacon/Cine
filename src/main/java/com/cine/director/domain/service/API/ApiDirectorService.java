package com.cine.director.domain.service.API;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.domain.service.API.IApiDetailActor;
import com.cine.director.DTO.ApiResponseDirector;
import com.cine.director.DTO.DirectorDetailDTO;
import com.cine.director.DTO.DirectorIdDTO;
import com.cine.utils.ConfigTMDB;
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
  private final IApiDetailActor apiDetailActor;

  public ApiDirectorService(
      RestTemplate restTemplate, ConfigTMDB token, IApiDetailActor apiDetailActor) {
    this.restTemplate = restTemplate;
    this.token = token;
    this.apiDetailActor = apiDetailActor;
  }

  @Override
  public DirectorIdDTO findIdDirector(Long idMovie) {
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
    return responseBody.crew().stream()
        .filter(crew -> crew.job().equals("Director"))
        .map(director -> new DirectorIdDTO(director.id(), director.job()))
        .findFirst()
        .orElseGet(() -> null);
  }

  @Override
  public DirectorDetailDTO findDirectorOfMovie(Long idMovie) {
    DetailActor directorAsActor = apiDetailActor.actor(findIdDirector(idMovie).id());

    return new DirectorDetailDTO(
        directorAsActor.id(),
        directorAsActor.name(),
        directorAsActor.birthday(),
        directorAsActor.profile_path(),
        directorAsActor.gender(),
        directorAsActor.place_of_birth(),
        directorAsActor.popularity(),
        directorAsActor.biography());
  }
}
