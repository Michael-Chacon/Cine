package com.cine.gender.domain.repository;

import com.cine.gender.persistence.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Long> {}
