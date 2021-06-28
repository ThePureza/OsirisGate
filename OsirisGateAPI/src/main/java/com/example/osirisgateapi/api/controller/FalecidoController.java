package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FalecidoDTO;
import com.example.osirisgateapi.model.entity.Falecido;
import com.example.osirisgateapi.service.FalecidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/falecidos")
@RequiredArgsConstructor
public class FalecidoController {

    private final FalecidoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Falecido> falecidos = service.getFalecidos();
        return ResponseEntity.ok(falecidos.stream().map(FalecidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Falecido> falecido = service.getFalecidoById(id);
        if(!falecido.isPresent()){
            return new ResponseEntity("Falecido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(falecido.map(FalecidoDTO::create));
    }
}
