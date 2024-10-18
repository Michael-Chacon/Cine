package com.cine.movie.domain.service.API;

import com.cine.movie.DTO.CastingDTO;

import java.util.List;

public interface IApiCasting {
  List<CastingDTO> getFullCast(Long movieId);
}
