package com.example.API_PokemonCards.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.API_PokemonCards.model.entity.Usuario;

import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
     Optional<Usuario> findByLogin(String login);
}
