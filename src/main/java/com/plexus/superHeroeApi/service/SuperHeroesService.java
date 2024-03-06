package com.plexus.superHeroeApi.service;

import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;

import java.util.List;
import java.util.Optional;

public interface SuperHeroesService {
    List<SuperHeroesEntity> searchAll();

    List<SuperHeroesEntity> filterName(String name);

    Optional<SuperHeroesEntity> findById(Long id);


    SuperHeroesEntity create(String name);

    SuperHeroesEntity update(SuperHeroesEntity superHeroe);

    void  deleteById(Long id);

}
