package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.OssuarioDTO;
import com.example.osirisgateapi.model.entity.Ossuario;
import com.example.osirisgateapi.service.OssuarioService;
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
@RequestMapping("/api/v1/ossuarios")
@RequiredArgsConstructor
public class OssuarioController {

    private final OssuarioService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Ossuario> ossuarios = service.getOssuarios();
        return ResponseEntity.ok(ossuarios.stream().map(OssuarioDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Ossuario> ossuario = service.getOssuarioById(id);
        if(!ossuario.isPresent()){
            return new ResponseEntity("Ossuário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(ossuario.map(OssuarioDTO::create));
    }
}
