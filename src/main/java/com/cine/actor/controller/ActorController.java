package com.cine.actor.controller;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.DTO.ResponseSearchActor;
import com.cine.actor.DTO.SearchActor;
import com.cine.actor.domain.service.API.IApiDetailActor;
import com.cine.actor.domain.service.API.IApiSearchActor;
import com.cine.actor.domain.service.IActor;
import com.cine.actor.persistence.Actor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/actors")
public class ActorController {
  @Autowired private IApiSearchActor apiSearchActorService;
  @Autowired private IApiDetailActor apiDetailActorService;
  @Autowired private IActor actorService;

  @GetMapping("/tmdb/search/{name}")
  public ResponseEntity<List<SearchActor>> searchActor(@PathVariable String name) {
    return ResponseEntity.ok(apiSearchActorService.SearchActor(name));
  }

  @GetMapping("/tmdb/detail/{id}")
  public ResponseEntity<DetailActor> detailActor(@PathVariable Long id) {
    DetailActor detailActor = apiDetailActorService.actor(id);
    if (detailActor != null) {
      actorService.save(detailActor);
    }
    return ResponseEntity.ok(detailActor);
  }

  @GetMapping
  public ResponseEntity<List<Actor>> actors() {
    return ResponseEntity.ok(actorService.findAll());
  }
}
