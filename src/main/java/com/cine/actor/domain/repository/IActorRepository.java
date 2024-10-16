package com.cine.actor.domain.repository;

import com.cine.actor.persistence.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActorRepository extends JpaRepository<Actor, Long> {}
