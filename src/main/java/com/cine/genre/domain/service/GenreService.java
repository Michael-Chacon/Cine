package com.cine.genre.domain.service;

import com.cine.genre.domain.repository.GenreRepository;
import com.cine.genre.persistence.Genre;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class GenreService implements IGenre {
  @Autowired GenreRepository repository;

  @Transactional(readOnly = true)
  @Override
  public Genre findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("El genero con el id " + id + " no esta registrado"));
  }

  @Transactional(readOnly = true)
  @Override
  public List<Genre> findAll() {
    return repository.findAll();
  }

  @Override
  public List<Genre> saveListOfGenres(Set<Genre> genre) {
    return repository.saveAllAndFlush(genre);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
