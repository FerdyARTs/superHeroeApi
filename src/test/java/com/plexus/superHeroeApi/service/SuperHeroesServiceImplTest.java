package com.plexus.superHeroeApi.service;


import com.plexus.superHeroeApi.exceptions.NotFoundException;
import com.plexus.superHeroeApi.persistence.entity.SuperHeroesEntity;
import com.plexus.superHeroeApi.persistence.repository.SuperHeroesRepository;
import com.plexus.superHeroeApi.service.impl.SuperHeroesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SuperHeroesServiceImplTest {

    @Mock
    private SuperHeroesRepository superHeroesRepository;

    @InjectMocks
    private SuperHeroesServiceImpl superHeroesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GIVEN  searchAll WHEN called  findAll THEN return OK")
    void searchAll_Ok() {
        List<SuperHeroesEntity> superHeroesList = new ArrayList<>();
        superHeroesList.add(new SuperHeroesEntity(1L, "Superman"));
        superHeroesList.add(new SuperHeroesEntity(2L, "Batman"));
        when(superHeroesRepository.findAll()).thenReturn(superHeroesList);

        List<SuperHeroesEntity> result = superHeroesService.searchAll();

        assertEquals(superHeroesList,result);
    }

    @Test
    @DisplayName("GIVEN  searchAll WHEN called  findAll THEN return NotFoundException")
    void searchAll_WhenNoSuperHeroes_KO() {
        when(superHeroesRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(NotFoundException.class, () -> superHeroesService.searchAll());
    }

    @Test
    @DisplayName("GIVEN  filterName WHEN called  findByNameContaining THEN return Ok")
    void filterName_WhenMatchesFound_ReturnsListOfSuperHeroes() {
        String name = "Spider";
        List<SuperHeroesEntity> expectedSuperHeroes = new ArrayList<>();
        SuperHeroesEntity spiderMan = new SuperHeroesEntity(1L, "Spider-Man");
        expectedSuperHeroes.add(spiderMan);

        when(superHeroesRepository.findByNameContaining(name)).thenReturn(expectedSuperHeroes);

        List<SuperHeroesEntity> result = superHeroesService.filterName(name);
        assertEquals(expectedSuperHeroes, result);
    }

    @Test
    @DisplayName("GIVEN  filterName WHEN called  findByNameContaining THEN return NotFoundException")
    void filterName_WhenNoMatchesFound_ThrowsRuntimeException() {

        String name = "Iron";
        when(superHeroesRepository.findByNameContaining(name)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> superHeroesService.filterName(name));
    }

    @Test
    @DisplayName("GIVEN  findById WHEN called  findById THEN return OK")
    void findById_Ok() {

        Long id = 1L;
        SuperHeroesEntity expectedSuperHeroe = new SuperHeroesEntity(id, "Spider-Man");
        when(superHeroesRepository.findById(id)).thenReturn(Optional.of(expectedSuperHeroe));

        Optional<SuperHeroesEntity> result = superHeroesService.findById(id);

        assertEquals(expectedSuperHeroe, result.orElse(null));
    }

    @Test
    @DisplayName("GIVEN  findById WHEN called  findById THEN return NotFoundException")
    void findById_KO() {
        Long id = 1L;
        when(superHeroesRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> superHeroesService.findById(id));
    }

    @Test
    @DisplayName("GIVEN  create WHEN called  save THEN return OK")
    void create_OK() {
        String name = "Iron Man";
        SuperHeroesEntity expectedSuperHeroe = new SuperHeroesEntity(1L, name);
        when(superHeroesRepository.save(any())).thenReturn(expectedSuperHeroe);

        SuperHeroesEntity result = superHeroesService.create(name);

        assertEquals(expectedSuperHeroe, result);
    }

    @Test
    @DisplayName("GIVEN  create WHEN called  save THEN return NotFoundException")
    void create_WhenRepositoryFails_ThrowsException() {
        String name = "Iron Man";
        when(superHeroesRepository.save(any())).thenThrow(new RuntimeException("Repository failure"));
        assertThrows(RuntimeException.class, () -> superHeroesService.create(name));
    }

    @Test
    @DisplayName("GIVEN  update WHEN called  save THEN return OK")
    void update_WhenSuperHeroeExists_ReturnsUpdatedSuperHeroeEntity() {
        Long id = 1L;
        String name = "Iron Man";
        SuperHeroesEntity existingSuperHeroe = new SuperHeroesEntity(id, "Spider-Man");
        SuperHeroesEntity updatedSuperHeroe = new SuperHeroesEntity(id, name);
        when(superHeroesRepository.findById(id)).thenReturn(Optional.of(existingSuperHeroe));
        when(superHeroesRepository.save(any())).thenReturn(updatedSuperHeroe);

        SuperHeroesEntity result = superHeroesService.update(updatedSuperHeroe);

        assertEquals(updatedSuperHeroe, result);
    }

    @Test
    @DisplayName("GIVEN  deleteById WHEN called  deleteById THEN return OK")
    void deleteById_WhenCalled_DeletesSuperHeroeById() {
        Long id = 1L;
        doNothing().when(superHeroesRepository).deleteById(id);
        superHeroesService.deleteById(id);

        verify(superHeroesRepository).deleteById(id);
    }
}
