package com.example.API_PokemonCards.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.API_PokemonCards.model.entity.Serie;
public interface SerieRepository extends JpaRepository<Serie, Long> {

}