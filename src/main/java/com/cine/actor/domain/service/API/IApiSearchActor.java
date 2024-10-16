package com.cine.actor.domain.service.API;

import com.cine.actor.DTO.ResponseSearchActor;
import com.cine.actor.DTO.SearchActor;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IApiSearchActor {
  List<SearchActor> SearchActor(String name);
}
