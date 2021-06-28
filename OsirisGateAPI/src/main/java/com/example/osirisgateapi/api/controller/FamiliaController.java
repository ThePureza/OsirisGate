package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FamiliaDTO;
import com.example.osirisgateapi.model.entity.Familia;
import com.example.osirisgateapi.service.FamiliaService;
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
@RequestMapping("/api/v1/familias")
@RequiredArgsConstructor
public class FamiliaController {

    private final FamiliaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Familia> familias = service.getFamilias();
        return ResponseEntity.ok(familias.stream().map(FamiliaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Familia> familia = service.getFamiliaById(id);
        if(!familia.isPresent()){
            return new ResponseEntity("Família não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(familia.map(FamiliaDTO::create));
    }
}
