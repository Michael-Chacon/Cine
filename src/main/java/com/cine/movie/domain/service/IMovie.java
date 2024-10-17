package com.cine.movie.domain.service;

import com.cine.movie.DTO.DetailsMovie;
import com.cine.movie.persistence.Movie;

import java.util.List;

public interface IMovie {
  Movie findById(Long id);

  List<Movie> findAll();

  DetailsMovie save(DetailsMovie movieDto);

  void delete(Long id);
}
