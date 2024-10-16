package com.cine.actor.DTO;

import java.time.LocalDate;

public record DetailActor(
    Long id,
    String name,
    LocalDate birthday,
    String profile_path,
    int gender,
    String place_of_birth,
    Double popularity,
    String biography) {}
