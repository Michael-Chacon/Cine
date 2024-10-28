package com.cine.director.domain.service;

import com.cine.director.domain.repository.DirectorRepository;
import com.cine.director.persistece.Director;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService implements IDirector {

  public DirectorRepository repository;

  public DirectorService(DirectorRepository repository) {
    this.repository = repository;
  }

  @Override
  public Director save(Director director) {
    return repository.save(director);
  }

  @Override
  public Director findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No existe un director con el ID " + id));
  }

  @Override
  public Optional<Director> findByIdForRegister(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<Director> listAll() {
    return repository.findAll();
  }
}
