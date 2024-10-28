package com.cine.director.controller;

import com.cine.director.DTO.DirectorDetailDTO;
import com.cine.director.DTO.DirectorIdDTO;
import com.cine.director.domain.service.API.IApiDirector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/director")
public class DirectorController {
  private final IApiDirector directorService;

  public DirectorController(IApiDirector directorService) {
    this.directorService = directorService;
  }

//  @GetMapping("/tmdb/details/{id}")
//  public ResponseEntity<DirectorDetailDTO> getDirector(@PathVariable Long id) {
//    return ResponseEntity.ok(directorService.findDirectorOfMovie(id));
//  }
}
