package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FunerariaDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Funeraria;
import com.example.osirisgateapi.service.FunerariaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity post(FunerariaDTO dto){
        try{
            Funeraria funeraria = converter(dto);
            funeraria = service.salvar(funeraria);
            return new ResponseEntity(funeraria, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FunerariaDTO dto){
        if(!service.getFunerariaById(id).isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Funeraria funeraria = converter(dto);
            funeraria.setId(id);
            service.salvar(funeraria);
            return ResponseEntity.ok(funeraria);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Funeraria> funeraria = service.getFunerariaById(id);
        if(!funeraria.isPresent()){
            return new ResponseEntity("Funerária não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(funeraria.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Funeraria converter(FunerariaDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Funeraria.class);
    }
}
