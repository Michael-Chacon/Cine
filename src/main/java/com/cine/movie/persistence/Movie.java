package com.cine.movie.persistence;

import com.cine.genre.persistence.Genre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "movies")
public class Movie {
  @Id Long id;
  @NotNull String title;
  Float vote_average;
  Date release_date;
  String poster_path;
  String backdrop_path;
  Double popularity;
  Long vote_count;
  Long runtime;

  @Column(columnDefinition = "TEXT")
  String overview;

  @ManyToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "movie_genres",
      joinColumns = @JoinColumn(name = "movie_id"),
      inverseJoinColumns = @JoinColumn(name = "genre_id"))
  List<Genre> genres;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Movie movie)) return false;
    return getId().equals(movie.getId())
        && getTitle().equals(movie.getTitle())
        && Objects.equals(getVote_average(), movie.getVote_average())
        && Objects.equals(getRelease_date(), movie.getRelease_date())
        && getPoster_path().equals(movie.getPoster_path())
        && Objects.equals(getBackdrop_path(), movie.getBackdrop_path())
        && getPopularity().equals(movie.getPopularity())
        && Objects.equals(getVote_count(), movie.getVote_count());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        getId(),
        getTitle(),
        getVote_average(),
        getRelease_date(),
        getPoster_path(),
        getBackdrop_path(),
        getPopularity(),
        getVote_count());
  }
}
