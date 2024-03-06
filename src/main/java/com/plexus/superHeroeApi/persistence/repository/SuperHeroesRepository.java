package com.plexus.superHeroeApi.persistence.repository;

import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SuperHeroesRepository extends JpaRepository<SuperHeroesEntity, Long> {
    List<SuperHeroesEntity> findByNameContaining(String name);

}
