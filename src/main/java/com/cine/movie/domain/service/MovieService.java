package com.cine.movie.domain.service;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.domain.repository.IActorRepository;
import com.cine.actor.domain.service.API.IApiDetailActor;
import com.cine.actor.persistence.Actor;
import com.cine.gender.domain.service.IGender;
import com.cine.gender.persistence.Gender;
import com.cine.genre.domain.repository.GenreRepository;
import com.cine.genre.persistence.Genre;
import com.cine.movie.DTO.CastingDTO;
import com.cine.movie.DTO.DetailsMovie;
import com.cine.movie.DTO.GenreDTO;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.domain.service.API.IApiCasting;
import com.cine.movie.persistence.Movie;
import com.cine.utils.Utils;
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
  @Autowired private IApiCasting castingService;
  @Autowired private IActorRepository actorRepository;
  @Autowired private IApiDetailActor detailActorService;
  @Autowired private IGender genderService;

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
    movie.setPoster_path(
        movieDto.poster_path() != null
            ? "https://image.tmdb.org/t/p/w500" + movieDto.poster_path()
            : null);
    movie.setBackdrop_path(
        movieDto.backdrop_path() != null
            ? "https://image.tmdb.org/t/p/original" + movieDto.backdrop_path()
            : null);
    movie.setPopularity(movieDto.popularity());
    movie.setOverview(movieDto.overview());
    movie.setRuntime(movieDto.runtime());
    // searchAndGetGenres: validar que los géneros existen en la db y agruparlos en una lista para
    // enviarlos a la entidad
    movie.setGenres(searchAndGetGenres(movieDto.genres()));

    List<CastingDTO> casting = castingService.getFullCast(movieDto.id());
    movie.setCasting(searchAndGetActor(casting));

    repository.save(movie);

    return movieDto;
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  public List<Genre> searchAndGetGenres(List<GenreDTO> genres) {
    List<Genre> listGenres = new ArrayList<>();

    for (GenreDTO genre : genres) {
      Optional<Genre> isThereGenre = genreRepository.findById(genre.id());

      if (isThereGenre.isPresent()) {
        listGenres.add(isThereGenre.get());
      } else {
        // Si el género no existe en la base de datos local, se buscará y registrará y se agregará a
        // la lista.
        Genre newGenre = new Genre(genre.id(), genre.name());
        listGenres.add(genreRepository.save(newGenre));
      }
    }
    return listGenres;
  }

  public List<Actor> searchAndGetActor(List<CastingDTO> casting) {
    List<Actor> listActors = new ArrayList<>();
    for (CastingDTO cast : casting) {
      Optional<Actor> isThereActor = actorRepository.findById(cast.id());
      if (isThereActor.isPresent()) {
        listActors.add(isThereActor.get());
      } else {
        DetailActor detailActor = detailActorService.actor(cast.id());
        if (detailActor.profile_path() != null && detailActor.gender() != 0) {
          Gender gender = genderService.findById(detailActor.gender());
          Actor actor =
              Actor.builder()
                  .id(detailActor.id())
                  .name(detailActor.name())
                  .birthday(
                      Utils.overcomeToDate(
                          detailActor.birthday() == null
                              ? "1977-07-21"
                              : detailActor.birthday().toString()))
                  .profilePath(detailActor.profile_path())
                  .placeOfBirth(detailActor.place_of_birth())
                  .popularity(detailActor.popularity())
                  .biography(detailActor.biography())
                  .gender(gender)
                  .build();
          listActors.add(actorRepository.save(actor));
        }
      }
    }
    return listActors;
  }
}
