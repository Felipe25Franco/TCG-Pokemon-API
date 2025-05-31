package com.example.API_PokemonCards.api.controller;

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
@RequestMapping("/api/v1/series")
@Api("API de Series")
@CrossOrigin

public class SerieController SerieController {
    private final SerieService service;

    @GetMapping
    @ApiOperation("Obter a lista de Series")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Series retornada com sucesso"),
            @ApiResponse(code  = 404, message  = "Serie não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get() {
        List<Serie> series = service.getSeries();
        return ResponseEntity.ok(series.stream().map(SereiDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Serie")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serie encontrada"),
            @ApiResponse(code  = 404, message  = "Serie não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Serie")  Long id) {
        Optional<Serie> serie = service.getSerieById(id);
        if (!serie.isPresent()) {
            return new ResponseEntity("Mundo não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(serie.map(SerieDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma Serie")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Serie salva com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar a Serie"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody SerieDTO dto) {
        try {
            Serie serie = converter(dto);
            serie = service.salvar(serie);
            return new ResponseEntity(serie, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Serie")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serie alterada com sucesso"),
            @ApiResponse(code  = 404, message  = "Serie não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody SerieDTO dto) {
        if (!service.getSerieById(id).isPresent()) {
            return new ResponseEntity("Serie não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Serie serie = converter(dto);
            serie.setId(id);
            service.salvar(serie);
            return ResponseEntity.ok(serie);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui uma Serie")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Serie excluída com sucesso"),
            @ApiResponse(code  = 404, message  = "Serie não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Serie> serie = service.getSerieById(id);
        if (!serie.isPresent()) {
            return new ResponseEntity("Serie não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(serie.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Serie converter(SerieDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Serie.class);
    }
}