package com.cine.actor.DTO;

import java.time.LocalDate;
import java.util.Date;

public record DetailActor(
    Long id,
    String name,
    LocalDate birthday,
    String profile_path,
    Long gender,
    String place_of_birth,
    Double popularity,
    String biography) {}
