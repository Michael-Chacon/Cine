package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.SearchMovie;

import java.util.List;

public interface IApiSearchMovie {
  List<SearchMovie> searchMovie(String name);
}
