package com.cine.gender.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "genders")
public class Gender {
  @Id Long genderId;
  @NotNull String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Gender gender)) return false;
    return getGenderId().equals(gender.getGenderId()) && getName().equals(gender.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getGenderId(), getName());
  }
}
