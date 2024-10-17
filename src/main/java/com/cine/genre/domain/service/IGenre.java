package com.cine.genre.domain.service;

import com.cine.genre.persistence.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IGenre {
  Genre findById(Long id);

  List<Genre> findAll();

  List<Genre> saveListOfGenres(Set<Genre> genre);

  Genre saveOneGenre(Genre genre);

  void delete(Long id);
}
