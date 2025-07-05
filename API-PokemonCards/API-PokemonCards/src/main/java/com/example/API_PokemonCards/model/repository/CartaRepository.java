package com.example.API_PokemonCards.model.repository;

import com.example.API_PokemonCards.model.entity.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaRepository extends JpaRepository<Carta, Long> {
}
