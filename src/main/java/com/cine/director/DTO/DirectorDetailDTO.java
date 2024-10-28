package com.cine.director.DTO;

import java.time.LocalDate;

public record DirectorDetailDTO(
        Long id,
        String name,
        LocalDate birthday,
        String profile_path,
        Long gender,
        String place_of_birth,
        Double popularity,
        String biography) {}
