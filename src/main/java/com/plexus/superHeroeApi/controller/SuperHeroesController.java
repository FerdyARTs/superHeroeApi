package com.plexus.superHeroeApi.controller;

import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;
import com.plexus.superHeroeApi.persistence.repository.SuperHeroesRepository;
import com.plexus.superHeroeApi.service.SuperHeroesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/superheroes")
public class SuperHeroesController {

    @Autowired
    protected SuperHeroesService superHeroesService;
    @Autowired
    protected SuperHeroesRepository superHeroesRepository;
    @GetMapping
    protected ResponseEntity<List<SuperHeroesEntity>> getAllSuperHeroes(){
            return ResponseEntity.ok(superHeroesService.searchAll());
    }

    @GetMapping("/getSuperHeroes/{id}")
    protected ResponseEntity<Optional<SuperHeroesEntity>> getSuperHeroesId(@PathVariable Long id){
        return ResponseEntity.ok(superHeroesService.findById(id));
    }
    @PostMapping("/create/{name}")
    protected ResponseEntity<SuperHeroesEntity> addSuperHeroes(@PathVariable String name){
        return ResponseEntity.ok(superHeroesService.create(name));
    }
    @PutMapping("/update/{id}")
    protected ResponseEntity<SuperHeroesEntity> upadateSuperHeroes(@PathVariable Long id, @RequestBody SuperHeroesEntity superHeroes){
        return ResponseEntity.ok(superHeroesService.update(superHeroes));
    }
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity<Void> deleteSuperHeroes(@PathVariable Long id){
        superHeroesService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public List<SuperHeroesEntity> getSuperHeroesContainingName(@RequestParam String name) {
        return ResponseEntity.ok(superHeroesService.filterName(name)).getBody();
    }
}
