package com.plexus.superHeroeApi.controller;

import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;
import com.plexus.superHeroeApi.service.SuperHeroesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuperHeroesControllerTest {

    @Mock
    private SuperHeroesService superHeroesService;

    @InjectMocks
    private SuperHeroesController superHeroesController;

    @Test
    public void testGetAllSuperHeroes() {
        List<SuperHeroesEntity> mockSuperHeroes = new ArrayList<>();
        mockSuperHeroes.add(new SuperHeroesEntity(1L, "Superman"));
        mockSuperHeroes.add(new SuperHeroesEntity(2L, "Batman"));

        when(superHeroesService.searchAll()).thenReturn(mockSuperHeroes);

        ResponseEntity<List<SuperHeroesEntity>> responseEntity = superHeroesController.getAllSuperHeroes();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSuperHeroes, responseEntity.getBody());
    }

    @Test
    public void testGetSuperHeroesId() {
        Long id = 1L;
        SuperHeroesEntity mockSuperHero = new SuperHeroesEntity();
        when(superHeroesService.findById(id)).thenReturn(Optional.of(mockSuperHero));
        ResponseEntity<Optional<SuperHeroesEntity>> responseEntity = superHeroesController.getSuperHeroesId(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(Optional.of(mockSuperHero), responseEntity.getBody());
    }

    @Test
    public void testAddSuperHeroes() {
        String name = "Superman";
        SuperHeroesEntity mockSuperHero = new SuperHeroesEntity();
        when(superHeroesService.create(name)).thenReturn(mockSuperHero);
        ResponseEntity<SuperHeroesEntity> responseEntity = superHeroesController.addSuperHeroes(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSuperHero, responseEntity.getBody());
    }

    @Test
    public void testUpdateSuperHeroes() {
        Long id = 1L;
        SuperHeroesEntity mockSuperHero = new SuperHeroesEntity();
        when(superHeroesService.update(any())).thenReturn(mockSuperHero);
        ResponseEntity<SuperHeroesEntity> responseEntity = superHeroesController.upadateSuperHeroes(id, new SuperHeroesEntity());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockSuperHero, responseEntity.getBody());
    }

    @Test
    public void testDeleteSuperHeroes() {
        Long id = 1L;
        ResponseEntity<Void> responseEntity = superHeroesController.deleteSuperHeroes(id);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void testGetSuperHeroesContainingName() {
        String name = "Superman";
        List<SuperHeroesEntity> mockSuperHeroes = new ArrayList<>();
        when(superHeroesService.filterName(name)).thenReturn(mockSuperHeroes);
        List<SuperHeroesEntity> result = superHeroesController.getSuperHeroesContainingName(name);
        assertEquals(mockSuperHeroes, result);
    }
}
