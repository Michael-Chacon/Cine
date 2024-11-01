package com.cine.movie.DTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record DetailsMovie(
    Long id,
    String title,
    Float vote_average,
    Date release_date,
    String poster_path,
    String backdrop_path,
    Double popularity,
    Long vote_count,
    List<CastingDTO> casting,
    List<GenreMovieDTO> genres,
    String overview,
    Long runtime,
    BigDecimal budget,
    BigDecimal revenue) {}
