package com.cine.director.domain.repository;

import com.cine.director.persistece.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Long> {}
