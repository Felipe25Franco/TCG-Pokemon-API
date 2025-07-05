package com.example.API_PokemonCards.service;

import com.example.API_PokemonCards.model.entity.Carta;
import com.example.API_PokemonCards.model.repository.CartaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CartaService {
    private CartaRepository repository;

    public CartaService (CartaRepository repository) {this.repository = repository;}
    public List<Carta> getCartas() {return repository.findAll();}
    public Optional<Carta> getCartaById(Long id) {return repository.findById(id);}

    @Transactional
    public Carta salvar(Carta carta) {
        validar(carta);
        return repository.save(carta);
    }

    @Transactional
    public void excluir(Carta carta) {
        Objects.requireNonNull(carta.getId());
        repository.delete(carta);
    }

    public void validar(Carta carta){

    }
}
