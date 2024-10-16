package com.cine.gender.domain.service;

import com.cine.gender.persistence.Gender;

public interface IGender {
    Gender findById(Long id);
}
