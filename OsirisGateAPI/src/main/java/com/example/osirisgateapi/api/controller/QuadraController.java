package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.QuadraDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.entity.Quadra;
import com.example.osirisgateapi.model.entity.Setor;
import com.example.osirisgateapi.service.QuadraService;
import com.example.osirisgateapi.service.SetorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/quadras")
@RequiredArgsConstructor
public class QuadraController {

    private final QuadraService service;
    private final SetorService setorService;

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

    @PostMapping()
    public ResponseEntity post(QuadraDTO dto){
        try{
            Quadra quadra = converter(dto);
            quadra = service.salvar(quadra);
            return new ResponseEntity(quadra, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, QuadraDTO dto){
        if(!service.getQuadraById(id).isPresent()){
            return new ResponseEntity("Quadra não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Quadra quadra = converter(dto);
            quadra.setId(id);
            service.salvar(quadra);
            return ResponseEntity.ok(quadra);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Quadra converter(QuadraDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Quadra quadra =  modelMapper.map(dto, Quadra.class);
        if (dto.getIdSetor() != null){
            Optional<Setor> setor = setorService.getSetorById(dto.getIdSetor());
            if(!setor.isPresent()){
                quadra.setSetor(null);
            } else {
                quadra.setSetor(setor.get());
            }
        }
        return quadra;
    }
}
