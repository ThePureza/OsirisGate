package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Programacao;
import com.example.osirisgateapi.model.repository.ProgramacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramacaoService {

    private ProgramacaoRepository repository;

    public ProgramacaoService(ProgramacaoRepository repository){
        this.repository = repository;
    }

    public List<Programacao> getProgramacoes(){
        return repository.findAll();
    }

    public Optional<Programacao> getProgramacaoById(Long id){
        return repository.findById(id);
    }
}
