package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.QuadraDTO;
import com.example.osirisgateapi.model.entity.Quadra;
import com.example.osirisgateapi.service.QuadraService;
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
@RequestMapping("/api/v1/quadras")
@RequiredArgsConstructor
public class QuadraController {

    private final QuadraService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Quadra> quadras = service.getQuadras();
        return ResponseEntity.ok(quadras.stream().map(QuadraDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Quadra> quadra = service.getQuadraById(id);
        if(!quadra.isPresent()){
            return new ResponseEntity("Quadra não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(quadra.map(QuadraDTO::create));
    }
}
