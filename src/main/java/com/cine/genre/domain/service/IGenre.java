package com.cine.genre.domain.service;

import com.cine.genre.persistence.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenre {
  Optional<Genre> findById(Long id);

  List<Genre> findAll();

  Genre save(Genre genre);

  Genre delete(Long id);
}
