package com.cine.director.domain.service.API;

import com.cine.director.DTO.DirectorDetailDTO;
import com.cine.director.DTO.DirectorIdDTO;

public interface IApiDirector {
  Long findIdDirector(Long idMovie);

  DirectorDetailDTO findDirectorById(Long idDirector);
}
