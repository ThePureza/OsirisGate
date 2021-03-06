package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.LoteDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.*;
import com.example.osirisgateapi.service.LoteService;
import com.example.osirisgateapi.service.QuadraService;
import com.example.osirisgateapi.service.SetorService;
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
@RequestMapping("/api/v1/lotes")
@RequiredArgsConstructor
@Api("API de Lotes")
public class LoteController {

    private final LoteService service;
    private final SetorService setorService;
    private final QuadraService quadraService;

    @GetMapping()
    @ApiOperation("Obter detalhes de todos os lotes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lote encontrado"),
            @ApiResponse(code = 404, message = "Lote não encontrado")
    })
    public ResponseEntity get(){
        List<Lote> lotes = service.getLotes();
        return ResponseEntity.ok(lotes.stream().map(LoteDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um lote específico")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Lote encontrado"),
            @ApiResponse(code = 404, message = "Lote não encontrado")
    })
    public ResponseEntity get(@PathVariable("id") @ApiParam("Id do lote") Long id){
        Optional<Lote> lote = service.getLoteById(id);
        if(!lote.isPresent()){
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lote.map(LoteDTO::create));
    }

    @PostMapping()
    @ApiOperation("Criar um lote")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Lote salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o lote")
    })
    public ResponseEntity post(LoteDTO dto){
        try{
            Lote lote = converter(dto);
            lote = service.salvar(lote);
            return new ResponseEntity(lote, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Alterar um lote")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Lote salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o lote")
    })
    public ResponseEntity atualizar(@PathVariable("id") @ApiParam("Id do lote") Long id, LoteDTO dto){
        if(!service.getLoteById(id).isPresent()){
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Lote lote = converter(dto);
            lote.setId(id);
            service.salvar(lote);
            return ResponseEntity.ok(lote);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Excluir um lote")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Lote excluído com sucesso")
    })
    public ResponseEntity excluir(@PathVariable("id") @ApiParam("Id do lote") Long id){
        Optional<Lote> lote = service.getLoteById(id);
        if(!lote.isPresent()){
            return new ResponseEntity("Lote não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(lote.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Lote converter(LoteDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Lote lote =  modelMapper.map(dto, Lote.class);
        if (dto.getIdSetor() != null){
            Optional<Setor> setor = setorService.getSetorById(dto.getIdSetor());
            if(!setor.isPresent()){
                lote.setSetor(null);
            } else {
                lote.setSetor(setor.get());
            }
        }
        if (dto.getIdQuadra() != null){
            Optional<Quadra> quadra = quadraService.getQuadraById(dto.getIdQuadra());
            if(!quadra.isPresent()){
                lote.setQuadra(null);
            } else {
                lote.setQuadra(quadra.get());
            }
        }
        return lote;
    }
}
