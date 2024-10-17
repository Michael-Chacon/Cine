package com.cine.movie.domain.service;

import com.cine.genre.domain.repository.GenreRepository;
import com.cine.genre.persistence.Genre;
import com.cine.movie.DTO.DetailsMovie;
import com.cine.movie.DTO.GenreOnlyId;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.persistence.Movie;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MovieService implements IMovie {
  @Autowired private MovieRepository repository;
  @Autowired private GenreRepository genreRepository;

  @Transactional(readOnly = true)
  @Override
  public Movie findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No existe una peli con el id " + id));
  }

  @Transactional(readOnly = true)
  @Override
  public List<Movie> findAll() {
    return repository.findAll();
  }

  @Override
  public DetailsMovie save(DetailsMovie movieDto) {
    Movie movie = new Movie();
    movie.setId(movieDto.id());
    movie.setTitle(movieDto.title());
    movie.setVote_average(movieDto.vote_average());
    movie.setVote_count(movieDto.vote_count());
    movie.setRelease_date(movieDto.release_date());
    movie.setPoster_path(movieDto.poster_path());
    movie.setBackdrop_path(movieDto.backdrop_path());
    movie.setPopularity(movieDto.popularity());
    movie.setOverview(movieDto.overview());
    movie.setGenres(addGenres(movieDto.genres()));
    movie.setRuntime(movieDto.runtime());
    repository.save(movie);
    return movieDto;
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public List<Genre> addGenres(List<GenreOnlyId> genres) {
    List<Genre> listGenres = new ArrayList<>();
    for (GenreOnlyId genreId : genres) {
      Optional<Genre> genre = genreRepository.findById(genreId.id());
      genre.ifPresent(listGenres::add);
    }
    return listGenres;
  }
}
