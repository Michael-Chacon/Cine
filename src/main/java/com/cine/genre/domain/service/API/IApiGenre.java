package com.cine.genre.domain.service.API;

import com.cine.genre.DTO.GenreResponse;
import com.cine.genre.persistence.Genre;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface IApiGenre {
  ResponseEntity<GenreResponse> getAll();
}
