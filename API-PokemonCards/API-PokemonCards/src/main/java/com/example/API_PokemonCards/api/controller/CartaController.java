package com.example.API_PokemonCards.api.controller;

import com.example.API_PokemonCards.api.DTO.CartaDTO;
import com.example.API_PokemonCards.api.DTO.SetDTO;
import com.example.API_PokemonCards.exception.RegraNegocioException;
import com.example.API_PokemonCards.model.entity.Carta;
import com.example.API_PokemonCards.model.entity.Serie;
import com.example.API_PokemonCards.model.entity.Set;
import com.example.API_PokemonCards.service.CartaService;
import com.example.API_PokemonCards.service.SetService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cartas")
@Api("API de Cartas")
@CrossOrigin
public class CartaController {
    private final CartaService service;
    private final SetService setService;

    @GetMapping
    @ApiOperation("Obter lista de Cartas")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Lista de Cartas retornada com sucesso"),
            @ApiResponse(code  = 404, message  = "Carta não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get() {
        List<Carta> cartas = service.getCartas();
        return ResponseEntity.ok(cartas.stream().map(CartaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de uma Carta")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Carta encontrada"),
            @ApiResponse(code  = 404, message  = "Carta não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id de Carta")  Long id) {
        Optional<Carta> carta = service.getCartaById(id);
        if (!carta.isPresent()) {
            return new ResponseEntity("Carta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(carta.map(CartaDTO::create));
    }

    @PostMapping
    @ApiOperation("Salva uma Carta")
    @ApiResponses({
            @ApiResponse(code  = 201, message  = "Carta salva com sucesso"),
            @ApiResponse(code  = 404, message  = "Erro ao salvar a Carta"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity post(@RequestBody CartaDTO dto) {
        try {
            Carta carta = converter(dto);
            carta = service.salvar(carta);
            return new ResponseEntity(carta, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Atualiza uma Carta")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Carta alterada com sucesso"),
            @ApiResponse(code  = 404, message  = "Carta não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody CartaDTO dto) {
        if (!service.getCartaById(id).isPresent()) {
            return new ResponseEntity("Carta não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Carta carta = converter(dto);
            carta.setId(id);
            service.salvar(carta);
            return ResponseEntity.ok(carta);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Exclui uma Carta")
    @ApiResponses({
            @ApiResponse(code  = 200, message  = "Carta excluída com sucesso"),
            @ApiResponse(code  = 404, message  = "Carta não encontrada"),
            @ApiResponse(code  = 500, message  = "Erro interno no servidor")
    })
    public ResponseEntity excluir(@PathVariable("id")  Long id) {
        Optional<Carta> carta = service.getCartaById(id);
        if (!carta.isPresent()) {
            return new ResponseEntity("Carta não encontrada", HttpStatus.NOT_FOUND);
    }
        try {
            service.excluir(carta.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Carta converter(CartaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Carta carta = modelMapper.map(dto, Carta.class);
        if (dto.getIdSet() != null) {
            Optional<Set> set = setService.getSetById(dto.getIdSet());
            if (!set.isPresent()) {
                carta.setSet(null);
            } else {
                carta.setSet(set.get());
            }
        }

        return carta;
    }
}
