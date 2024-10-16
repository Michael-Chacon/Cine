package com.cine.gender.domain.service;

import com.cine.gender.domain.repository.GenderRepository;
import com.cine.gender.persistence.Gender;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenderService implements IGender {
  @Autowired private GenderRepository repository;

  @Override
  public Gender findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No existe un genero con el id " + id));
  }
}
