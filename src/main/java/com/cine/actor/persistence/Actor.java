package com.cine.actor.persistence;

import com.cine.gender.persistence.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "actors")
public class Actor {
  @Id Long id;

  @NotNull
  @Size(max = 255)
  String name;

  Date birthday;

  @Size(max = 255)
  String profilePath;

  @Size(max = 255)
  String placeOfBirth;

  Double popularity;

  @Column(columnDefinition = "TEXT")
  String biography;

  @ManyToOne(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "genderId")
  Gender gender;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Actor actor)) return false;
    return getId().equals(actor.getId())
        && getName().equals(actor.getName())
        && getBirthday().equals(actor.getBirthday())
        && Objects.equals(getProfilePath(), actor.getProfilePath())
        && getPlaceOfBirth().equals(actor.getPlaceOfBirth())
        && getPopularity().equals(actor.getPopularity())
        && getBiography().equals(actor.getBiography());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getName(),
        getBirthday(),
        getProfilePath(),
        getPlaceOfBirth(),
        getPopularity(),
        getBiography());
  }
}
