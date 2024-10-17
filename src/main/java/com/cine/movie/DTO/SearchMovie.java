package com.cine.movie.DTO;

import java.util.Date;

public record SearchMovie(
    Long id, String title, Float vote_average, Date release_date, String poster_path) {}
