package com.example.API_PokemonCards.service;

import com.example.API_PokemonCards.model.entity.Set;
import com.example.API_PokemonCards.model.repository.SetRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
@Service
public class SetService {
    private SetRepository repository;

    public SetService (SetRepository repository) {
        this.repository = repository;
    }

    public List<Set> getSets() {return repository.findAll();}
    public Optional<Set> getSetById(Long id) {return repository.findById(id);}

    @Transactional
    public Set salvar(Set set) {
        validar(set);
        return repository.save(set);
    }

    @Transactional
    public void excluir(Set set) {
        Objects.requireNonNull(set.getId());
        repository.delete(set);
    }

    public void validar(Set set){

    }

}
