package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.ServicoDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Servico;
import com.example.osirisgateapi.service.ServicoService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/servicos")
@RequiredArgsConstructor
@Api("API de Serviços")
public class ServicoController {

    private final ServicoService service;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os setores")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Setor encontrado"),
            @ApiResponse(code = 404, message = "Setor não encontrado")
    })
    public ResponseEntity get(){
        List<Servico> servicos = service.getServicos();
        return ResponseEntity.ok(servicos.stream().map(ServicoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um setor específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Setor encontrado"),
            @ApiResponse(code = 404, message = "Setor não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do setor") Long id){
        Optional<Servico> servico = service.getServicoById(id);
        if(!servico.isPresent()){
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(servico.map(ServicoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar um setor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Setor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o setor")
    })
    public ResponseEntity post(ServicoDTO dto){
        try{
            Servico servico = converter(dto);
            servico = service.salvar(servico);
            return new ResponseEntity(servico, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um setor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Setor salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o setor")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do setor") Long id, ServicoDTO dto){
        if(!service.getServicoById(id).isPresent()){
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Servico servico = converter(dto);
            servico.setId(id);
            service.salvar(servico);
            return ResponseEntity.ok(servico);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um setor")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Setor excluído com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do setor") Long id){
        Optional<Servico> servico = service.getServicoById(id);
        if(!servico.isPresent()){
            return new ResponseEntity("Serviço não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(servico.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Servico converter(ServicoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Servico.class);
    }
}
