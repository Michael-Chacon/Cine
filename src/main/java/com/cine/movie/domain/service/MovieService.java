package com.cine.movie.domain.service;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.domain.repository.IActorRepository;
import com.cine.actor.domain.service.API.IApiDetailActor;
import com.cine.actor.persistence.Actor;
import com.cine.director.DTO.DirectorDetailDTO;
import com.cine.director.domain.service.API.IApiDirector;
import com.cine.director.domain.service.IDirector;
import com.cine.director.persistece.Director;
import com.cine.gender.domain.service.IGender;
import com.cine.gender.persistence.Gender;
import com.cine.genre.domain.repository.GenreRepository;
import com.cine.genre.persistence.Genre;
import com.cine.movie.DTO.CastingDTO;
import com.cine.movie.DTO.DetailsMovie;
import com.cine.movie.DTO.GenreMovieDTO;
import com.cine.movie.domain.repository.MovieRepository;
import com.cine.movie.domain.service.API.IApiCasting;
import com.cine.movie.persistence.Movie;
import com.cine.utils.Utils;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cine.utils.Constants.DEFAULT_BIRTHDAY;

@Transactional
@Service
public class MovieService implements IMovie {
  private final MovieRepository repository;
  private final GenreRepository genreRepository;
  private final IApiCasting castingService;
  private final IActorRepository actorRepository;
  private final IApiDetailActor detailActorService;
  private final IGender genderService;
  private final IApiDirector apiDirectorService;
  private final IDirector directorService;

  public MovieService(
      MovieRepository repository,
      GenreRepository genreRepository,
      IApiCasting castingService,
      IActorRepository actorRepository,
      IApiDetailActor detailActorService,
      IGender genderService,
      IApiDirector apiDirectorService,
      IDirector directorService) {
    this.repository = repository;
    this.genreRepository = genreRepository;
    this.castingService = castingService;
    this.actorRepository = actorRepository;
    this.detailActorService = detailActorService;
    this.genderService = genderService;
    this.apiDirectorService = apiDirectorService;
    this.directorService = directorService;
  }

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

  //  Método para guardar en la base de datos una película que viene de la API de TMDB.
  @Override
  public DetailsMovie save(DetailsMovie movieDto) {
    Movie movie = new Movie();
    movie.setId(movieDto.id());
    movie.setTitle(movieDto.title());
    movie.setVote_average(movieDto.vote_average());
    movie.setVote_count(movieDto.vote_count());
    movie.setRelease_date(movieDto.release_date());
    movie.setBudget(movieDto.budget());
    movie.setRevenue(movieDto.revenue());
    //    Si la img no es nula, colocamos la URL base para poder mostrar las imágenes.
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

    movie.setGenres(searchAndGetGenres(movieDto.genres()));
    //    Obtener la lista de ID de los actores que participan en la película.
    List<CastingDTO> casting = castingService.getFullCast(movieDto.id());
    //    Obtener los actores y relacionarlos con la peli.
    movie.setCasting(searchAndGetActors(casting));
    // Obtener el director y relacionarlo con la peli
    movie.setDirector(searchAndGetDirector(movieDto.id()));

    repository.save(movie);

    return movieDto;
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  // Método que busca y obtiene géneros con base a una lista GenreMovieDTO
  @Transactional
  public List<Genre> searchAndGetGenres(List<GenreMovieDTO> genres) {
    return genres.stream()
        .map(genre -> genreRepository.findById(genre.id()).orElseGet(() -> createNewGenre(genre)))
        .collect(Collectors.toList());
  }

  // Método auxiliar que crea un género si no existe en la base de datos
  public Genre createNewGenre(GenreMovieDTO genreMovieDTO) {
    Genre genre = new Genre(genreMovieDTO.id(), genreMovieDTO.name());
    return genreRepository.save(genre);
  }

  // Método que busca y obtiene actores con base en una lista de CastingDTO
  @Transactional
  public List<Actor> searchAndGetActors(List<CastingDTO> castingDTOS) {

    return castingDTOS.stream()
        .map(cast -> actorRepository.findById(cast.id()).orElseGet(() -> createNewActor(cast.id())))
        .filter(Objects::nonNull)
        // Registrar solo los 10 primeros actores de la película
        .limit(10)
        .collect(Collectors.toList());
  }

  //  Método auxiliar que crea un nuevo actor si existe en la base de datos
  public Actor createNewActor(Long id) {

    DetailActor detailActor = detailActorService.actor(id);
    if (detailActor.profile_path() != null && detailActor.gender() != 0) {
      Gender gender = genderService.findById(detailActor.gender());
      return Actor.builder()
          .id(detailActor.id())
          .name(detailActor.name())
          .birthday(
              Utils.overcomeToDate(
                  detailActor.birthday() == null
                      ? DEFAULT_BIRTHDAY
                      : detailActor.birthday().toString()))
          .profilePath(
              detailActor.profile_path() != null
                  ? "https://image.tmdb.org/t/p/w500/" + detailActor.profile_path()
                  : null)
          .placeOfBirth(detailActor.place_of_birth())
          .popularity(detailActor.popularity())
          .biography(detailActor.biography())
          .gender(gender)
          .build();
    }
    return null;
  }

  public Director searchAndGetDirector(Long idMovie) {
    Long idDirector = apiDirectorService.findIdDirector(idMovie);
    return directorService
        .findByIdForRegister(idDirector)
        .orElseGet(() -> createNewDirector(idDirector));
  }

  public Director createNewDirector(Long id) {
    DirectorDetailDTO directorDTO = apiDirectorService.findDirectorById(id);
    Gender gender = genderService.findById(directorDTO.gender());
    return new Director(
        directorDTO.id(),
        directorDTO.name(),
        Utils.overcomeToDate(directorDTO.birthday().toString()),
        directorDTO.profile_path() != null
            ? "https://image.tmdb.org/t/p/w500/" + directorDTO.profile_path()
            : null,
        directorDTO.place_of_birth(),
        directorDTO.popularity(),
        directorDTO.biography(),
        gender);
  }
}
