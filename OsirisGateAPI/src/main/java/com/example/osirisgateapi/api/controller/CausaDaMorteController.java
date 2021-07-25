package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CausaDaMorteDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.CausaDaMorte;
import com.example.osirisgateapi.service.CausaDaMorteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public ResponseEntity post(CausaDaMorteDTO dto){
        try{
            CausaDaMorte causaDaMorte = converter(dto);
            causaDaMorte = service.salvar(causaDaMorte);
            return new ResponseEntity(causaDaMorte, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, CausaDaMorteDTO dto){
        if(!service.getCausaDaMorteById(id).isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            CausaDaMorte causaDaMorte = converter(dto);
            causaDaMorte.setId(id);
            service.salvar(causaDaMorte);
            return ResponseEntity.ok(causaDaMorte);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<CausaDaMorte> causaDaMorte = service.getCausaDaMorteById(id);
        if(!causaDaMorte.isPresent()){
            return new ResponseEntity("Causa da morte não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(causaDaMorte.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public CausaDaMorte converter(CausaDaMorteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, CausaDaMorte.class);
    }
}
