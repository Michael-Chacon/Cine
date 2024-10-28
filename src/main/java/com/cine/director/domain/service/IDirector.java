package com.cine.director.domain.service;

import com.cine.director.persistece.Director;

import java.util.List;
import java.util.Optional;

public interface IDirector {
    Director save(Director director);
    Director findById(Long id);
    Optional<Director> findByIdForRegister(Long id);
    List<Director> listAll();
}