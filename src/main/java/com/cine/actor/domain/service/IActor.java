package com.cine.actor.domain.service;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.persistence.Actor;

import java.util.List;
import java.util.Set;

public interface IActor {
  Actor findById(Long id);

  List<Actor> findAll();

  DetailActor save(DetailActor actor);

  void delete(Long id);
}
