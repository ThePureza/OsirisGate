package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.OssuarioDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;;
import com.example.osirisgateapi.model.entity.Ossuario;
import com.example.osirisgateapi.service.OssuarioService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity post(OssuarioDTO dto){
        try{
            Ossuario ossuario = converter(dto);
            ossuario = service.salvar(ossuario);
            return new ResponseEntity(ossuario, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, OssuarioDTO dto){
        if(!service.getOssuarioById(id).isPresent()){
            return new ResponseEntity("Ossuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Ossuario ossuario = converter(dto);
            ossuario.setId(id);
            service.salvar(ossuario);
            return ResponseEntity.ok(ossuario);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Ossuario> ossuario = service.getOssuarioById(id);
        if(!ossuario.isPresent()){
            return new ResponseEntity("Ossuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(ossuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Ossuario converter(OssuarioDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Ossuario.class);
    }
}
