package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.ProgramacaoDTO;
import com.example.osirisgateapi.model.entity.Programacao;
import com.example.osirisgateapi.service.ProgramacaoService;
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
@RequestMapping("/api/v1/programacoes")
@RequiredArgsConstructor
public class ProgramacaoController {

    private final ProgramacaoService service;

    @GetMapping()
    public ResponseEntity get(){
        List<Programacao> programacoes = service.getProgramacoes();
        return ResponseEntity.ok(programacoes.stream().map(ProgramacaoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Programacao> programacao = service.getProgramacaoById(id);
        if(!programacao.isPresent()){
            return new ResponseEntity("Programação não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(programacao.map(ProgramacaoDTO::create));
    }
}
