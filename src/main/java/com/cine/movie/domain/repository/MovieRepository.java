package com.cine.movie.domain.repository;

import com.cine.movie.persistence.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {}
