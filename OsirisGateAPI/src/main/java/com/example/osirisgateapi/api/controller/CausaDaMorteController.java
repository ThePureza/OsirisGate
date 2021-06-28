package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CausaDaMorteDTO;
import com.example.osirisgateapi.model.entity.CausaDaMorte;
import com.example.osirisgateapi.service.CausaDaMorteService;
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
@RequestMapping("/api/v1/causaDaMortes")
@RequiredArgsConstructor
public class CausaDaMorteController {

    private final CausaDaMorteService service;

    @GetMapping()
    public ResponseEntity get(){
        List<CausaDaMorte> causaDaMortes = service.getCausaDaMortes();
        return ResponseEntity.ok(causaDaMortes.stream().map(CausaDaMorteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<CausaDaMorte> causaDaMorte = service.getCausaDaMorteById(id);
        if(!causaDaMorte.isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(causaDaMorte.map(CausaDaMorteDTO::create));
    }
}
