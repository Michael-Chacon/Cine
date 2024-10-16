package com.cine.actor.domain.service;

import com.cine.actor.DTO.DetailActor;
import com.cine.actor.domain.repository.IActorRepository;
import com.cine.actor.persistence.Actor;
import com.cine.gender.domain.service.IGender;
import com.cine.gender.persistence.Gender;
import com.cine.utils.Utils;
import com.cine.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ActorService implements IActor {
  @Autowired IActorRepository repository;
  @Autowired IGender genderService;

  @Transactional(readOnly = true)
  @Override
  public Actor findById(Long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No existe un actor con el id " + id));
  }

  @Transactional(readOnly = true)
  @Override
  public List<Actor> findAll() {
    return repository.findAll();
  }

  @Override
  public DetailActor save(DetailActor actorDto) {
    Actor actor = new Actor();
    actor.setId(actorDto.id());
    actor.setName(actorDto.name());
    actor.setBirthday(Utils.overcomeToDate(actorDto.birthday().toString()));
    Gender gender = genderService.findById(actorDto.gender());
    actor.setGender(gender);
    actor.setProfilePath("https://image.tmdb.org/t/p/w500" + actorDto.profile_path());
    actor.setPlaceOfBirth(actorDto.place_of_birth());
    actor.setPopularity(actorDto.popularity());
    actor.setBiography(actorDto.biography());
    repository.save(actor);
    return actorDto;
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }
}
