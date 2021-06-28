package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Servico;
import com.example.osirisgateapi.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository){
        this.repository = repository;
    }

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id){
        return repository.findById(id);
    }
}
