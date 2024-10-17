package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.DetailsMovie;

public interface IApiDetailsMovie {
  DetailsMovie getMovie(Long id);
}
