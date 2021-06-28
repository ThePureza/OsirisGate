package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.GavetaDTO;
import com.example.osirisgateapi.model.entity.Gaveta;
import com.example.osirisgateapi.service.GavetaService;
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
@RequestMapping("/api/v1/gavetas")
@RequiredArgsConstructor
public class GavetaController {

    private final GavetaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Gaveta> gavetas = service.getGavetas();
        return ResponseEntity.ok(gavetas.stream().map(GavetaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Gaveta> gaveta = service.getGavetaById(id);
        if(!gaveta.isPresent()){
            return new ResponseEntity("Gaveta não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(gaveta.map(GavetaDTO::create));
    }
}
