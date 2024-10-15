package com.cine.genre.DTO;

import com.cine.genre.persistence.Genre;

import java.util.List;

public record GenreResponse(List<Genre> genres) {}
