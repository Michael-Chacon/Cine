package com.cine.genre.domain.service.API;

import com.cine.genre.DTO.GenreDTO;

import java.util.List;

public interface IApiGenre {
  List<GenreDTO> getAllGenres();
}
