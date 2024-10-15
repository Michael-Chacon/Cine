package com.cine.genre.controller;

import com.cine.genre.DTO.GenreResponse;
import com.cine.genre.domain.service.API.IApiGenre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
  @Autowired private IApiGenre genreApiService;

  @GetMapping("/tmdb")
  public ResponseEntity<GenreResponse> getAll() {
    return genreApiService.getAll();
  }
}
