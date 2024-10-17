package com.cine.movie.controller;

import com.cine.movie.DTO.DetailsMovie;
import com.cine.movie.DTO.SearchMovie;
import com.cine.movie.domain.service.API.ApiDetailsMovieService;
import com.cine.movie.domain.service.API.IApiSearchMovie;
import com.cine.movie.domain.service.IMovie;
import com.cine.movie.persistence.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
public class MovieController {
  @Autowired private IApiSearchMovie apiSearchMovie;
  @Autowired private ApiDetailsMovieService apiDetailsMovieService;
  @Autowired private IMovie movieService;

  @GetMapping("/tmdb/search/{name}")
  public ResponseEntity<List<SearchMovie>> searchMovie(@PathVariable String name) {
    return ResponseEntity.ok(apiSearchMovie.searchMovie(name));
  }

  @GetMapping("/tmdb/details/{id}")
  public DetailsMovie details(@PathVariable Long id) {
    DetailsMovie movie = apiDetailsMovieService.getMovie(id);

    if (movie != null) {
      movieService.save(movie);
    }
    return movie;
  }

  @GetMapping
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Movie> findById(@PathVariable Long id) {
    return ResponseEntity.ok(movieService.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
    Map<String, String> response = Map.of("Message", "Recurso eliminado con éxito");
    movieService.delete(id);
    return ResponseEntity.ok(response);
  }
}
