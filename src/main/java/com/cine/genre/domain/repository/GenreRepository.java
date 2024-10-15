package com.cine.genre.domain.repository;

import com.cine.genre.persistence.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {}
