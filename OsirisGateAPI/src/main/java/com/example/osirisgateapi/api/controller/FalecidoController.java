package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.FalecidoDTO;
import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.*;
import com.example.osirisgateapi.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/falecidos")
@RequiredArgsConstructor
public class FalecidoController {

    private final FalecidoService service;
    private final FamiliaService familiaService;
    private final FunerariaService funerariaService;
    private final CausaDaMorteService causaDaMorteService;
    private final ServicoService servicoService;
    private final SetorService setorService;
    private final QuadraService quadraService;

    @GetMapping()
    public ResponseEntity get(){
        List<Falecido> falecidos = service.getFalecidos();
        return ResponseEntity.ok(falecidos.stream().map(FalecidoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id){
        Optional<Falecido> falecido = service.getFalecidoById(id);
        if(!falecido.isPresent()){
            return new ResponseEntity("Falecido não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(falecido.map(FalecidoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(FalecidoDTO dto){
        try{
            Falecido falecido = converter(dto);
            falecido = service.salvar(falecido);
            return new ResponseEntity(falecido, HttpStatus.CREATED);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, FalecidoDTO dto){
        if(!service.getFalecidoById(id).isPresent()){
            return new ResponseEntity("Falecido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Falecido falecido = converter(dto);
            falecido.setId(id);
            service.salvar(falecido);
            return ResponseEntity.ok(falecido);
        } catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id){
        Optional<Falecido> falecido = service.getFalecidoById(id);
        if(!falecido.isPresent()){
            return new ResponseEntity("Falecido não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(falecido.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Falecido converter(FalecidoDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        Falecido falecido =  modelMapper.map(dto, Falecido.class);
        if (dto.getIdFamilia() != null){
            Optional<Familia> familia = familiaService.getFamiliaById(dto.getIdFamilia());
            if(!familia.isPresent()){
                falecido.setFamilia(null);
            } else {
                falecido.setFamilia(familia.get());
            }
        }
        if (dto.getIdFuneraria() != null){
            Optional<Funeraria> funeraria = funerariaService.getFunerariaById(dto.getIdFuneraria());
            if(!funeraria.isPresent()){
                falecido.setFuneraria(null);
            } else {
                falecido.setFuneraria(funeraria.get());
            }
        }
        if (dto.getIdCausaDaMorte() != null){
            Optional<CausaDaMorte> causaDaMorte = causaDaMorteService.getCausaDaMorteById(dto.getIdCausaDaMorte());
            if(!causaDaMorte.isPresent()){
                falecido.setCausaDaMorte(null);
            } else {
                falecido.setCausaDaMorte(causaDaMorte.get());
            }
        }
        if (dto.getIdServico() != null){
            Optional<Servico> servico = servicoService.getServicoById(dto.getIdServico());
            if(!servico.isPresent()){
                falecido.setServico(null);
            } else {
                falecido.setServico(servico.get());
            }
        }
        if (dto.getIdSetor() != null){
            Optional<Setor> setor = setorService.getSetorById(dto.getIdSetor());
            if(!setor.isPresent()){
                falecido.setSetor(null);
            } else {
                falecido.setSetor(setor.get());
            }
        }
        if (dto.getIdQuadra() != null){
            Optional<Quadra> quadra = quadraService.getQuadraById(dto.getIdQuadra());
            if(!quadra.isPresent()){
                falecido.setQuadra(null);
            } else {
                falecido.setQuadra(quadra.get());
            }
        }
        return falecido;
    }
}
