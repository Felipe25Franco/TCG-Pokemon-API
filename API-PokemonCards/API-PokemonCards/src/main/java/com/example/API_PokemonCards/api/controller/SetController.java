package com.example.API_PokemonCards.api.controller;

import com.example.API_PokemonCards.api.DTO.SetDTO;
import com.example.API_PokemonCards.model.entity.Serie;
import com.example.API_PokemonCards.model.entity.Set;
import com.example.API_PokemonCards.service.SerieService;
import com.example.API_PokemonCards.service.SetService;
import com.example.API_PokemonCards.exception.RegraNegocioException;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sets")
@Api("API de Sets")
@CrossOrigin
public class SetController {
    private final SetService service;
    private final SerieService serieService;

    @GetMapping
    @ApiOperation("Obter a lista de Sets")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Sets retornada com sucesso"),
            @ApiResponse(code  = 404, message  = "Set não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get() {
        List<Set> sets = service.getSets();
        return ResponseEntity.ok(sets.stream().map(SetDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um Set")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Set encontrado"),
            @ApiResponse(code  = 404, message  = "Set não encontrad"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Set")  Long id) {
        Optional<Set> set = service.getSetById(id);
        if (!set.isPresent()) {
            return new ResponseEntity("Set não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(set.map(SetDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva um Set")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Set salvo com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar o Set"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody SetDTO dto) {
        try {
            Set set = converter(dto);
            set = service.salvar(set);
            return new ResponseEntity(set, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza um Set")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Set alterado com sucesso"),
            @ApiResponse(code  = 404, message  = "Set não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SetDTO dto) {
        if (!service.getSetById(id).isPresent()) {
            return new ResponseEntity("Set não encontradO", HttpStatus.NOT_FOUND);
        }
        try {
            Set set = converter(dto);
            set.setId(id);
            service.salvar(set);
            return ResponseEntity.ok(set);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui um Set")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Set excluído com sucesso"),
            @ApiResponse(code  = 404, message  = "Set não encontrado"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Set> set = service.getSetById(id);
        if (!set.isPresent()) {
            return new ResponseEntity("Set não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(set.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Set converter(SetDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Set set = modelMapper.map(dto, Set.class);
        if (dto.getIdSerie() != null) {
            Optional<Serie> serie = serieService.getSerieById(dto.getIdSerie());
            if (!serie.isPresent()) {
                set.setSerie(null);
            } else {
                set.setSerie(serie.get());
            }
        }

        return set;
    }
}
