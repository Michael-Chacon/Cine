package com.cine.actor.controller;

import com.cine.actor.DTO.ResponseSearchActor;
import com.cine.actor.DTO.SearchActor;
import com.cine.actor.domain.service.API.IApiSearchActor;
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

@RestController
@RequestMapping("/actors")
public class ActorController {
  @Autowired private IApiSearchActor apiSearchActorService;

  @GetMapping("/tmdb/{name}")
  public ResponseEntity<List<SearchActor>> searchActor(@PathVariable String name) throws JsonProcessingException {
    return ResponseEntity.ok(apiSearchActorService.SearchActor(name));
  }
}
