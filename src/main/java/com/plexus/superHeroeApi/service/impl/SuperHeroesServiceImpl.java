package com.plexus.superHeroeApi.service.impl;

import com.plexus.superHeroeApi.exceptions.ConflictException;
import com.plexus.superHeroeApi.exceptions.NotFoundException;
import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;
import com.plexus.superHeroeApi.persistence.repository.SuperHeroesRepository;
import com.plexus.superHeroeApi.service.SuperHeroesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

 @Service
 @RequiredArgsConstructor
public class SuperHeroesServiceImpl  implements SuperHeroesService {

    private final SuperHeroesRepository superHeroesRepository;

    @Override
    public List<SuperHeroesEntity> searchAll() {
        return superHeroesRepository.findAll()
                .stream()
                .findAny().map(e -> superHeroesRepository.findAll().stream())
                .orElseThrow(() -> new NotFoundException("SuperHeroes not found")).toList();
    }

     @Override
     public List<SuperHeroesEntity> filterName(String name) {
         return Optional.ofNullable(superHeroesRepository.findByNameContaining(name))
                 .orElseThrow(() -> new RuntimeException("No matches found"));
     }

     @Override
    public Optional<SuperHeroesEntity> findById(Long id) {
        return Optional.ofNullable(superHeroesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SuperHeroe id: " + id)));
    }

    @Override
    public SuperHeroesEntity create(String name) {
        SuperHeroesEntity superHeroes = new SuperHeroesEntity();
        superHeroes.setName(name);
        SuperHeroesEntity superHeroesEntity = superHeroesRepository.save(superHeroes);
        return superHeroesEntity;

    }

    @Override
    public SuperHeroesEntity update(SuperHeroesEntity superHeroe) {
        return superHeroesRepository.findById(superHeroe.getId())
                .map(heroesEntity -> {
                    SuperHeroesEntity superHeroesEntity = new SuperHeroesEntity();
                    superHeroesEntity.setId(superHeroe.getId());
                    superHeroesEntity.setName(superHeroe.getName());
                    return superHeroesRepository.save(superHeroesEntity);
                })
                .orElse(null);

    }

    @Override
    public void deleteById(Long id) {
        superHeroesRepository.deleteById(id);
    }
     private void IdIsNotNull(Long id){
         superHeroesRepository.findById(id).ifPresent(superHeroe -> {
             throw new ConflictException("SuperHeroe Id exists" + id);
         });
     }
 }

