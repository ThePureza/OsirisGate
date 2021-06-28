package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FunerariaDTO;
import com.example.osirisgateapi.model.entity.Funeraria;
import com.example.osirisgateapi.service.FunerariaService;
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
@RequestMapping("/api/v1/funerarias")
@RequiredArgsConstructor
public class FunerariaController {

    private final FunerariaService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Funeraria> funerarias = service.getFunerarias();
        return ResponseEntity.ok(funerarias.stream().map(FunerariaDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Funeraria> funeraria = service.getFunerariaById(id);
        if(!funeraria.isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(funeraria.map(FunerariaDTO::create));
    }
}
