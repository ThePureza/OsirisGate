package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.SetorDTO;
import com.example.osirisgateapi.model.entity.Setor;
import com.example.osirisgateapi.service.SetorService;
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
@RequestMapping("/api/v1/setores")
@RequiredArgsConstructor
public class SetorController {

    private final SetorService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Setor> setores = service.getSetores();
        return ResponseEntity.ok(setores.stream().map(SetorDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Setor> setor = service.getSetorById(id);
        if(!setor.isPresent()){
            return new ResponseEntity("Setor não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(setor.map(SetorDTO::create));
    }
}
