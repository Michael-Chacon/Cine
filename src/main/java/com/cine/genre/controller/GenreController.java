package com.cine.genre.controller;

import com.cine.genre.DTO.GenreDTO;
import com.cine.genre.DTO.GenreResponse;
import com.cine.genre.domain.service.API.IApiGenre;
import com.cine.genre.domain.service.IGenre;
import com.cine.genre.persistence.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/genres")
public class GenreController {
  @Autowired private IApiGenre genreApiService;
  @Autowired private IGenre service;

  @GetMapping("/tmdb")
  public ResponseEntity<List<GenreDTO>> getAllForAPI() {
    return ResponseEntity.ok(genreApiService.getAllGenres());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Genre> oneGenre(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @GetMapping
  public ResponseEntity<List<Genre>> AllGenre() {
    return ResponseEntity.ok(service.findAll());
  }

  @PostMapping("/save")
  public ResponseEntity<List<Genre>> save(@RequestBody Set<Genre> genres) {
    return ResponseEntity.ok(service.saveListOfGenres(genres));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
    Map<String, String> response = Map.of("message", "Genero eliminado con éxito");
    service.delete(id);
    return ResponseEntity.ok(response);
  }
}
