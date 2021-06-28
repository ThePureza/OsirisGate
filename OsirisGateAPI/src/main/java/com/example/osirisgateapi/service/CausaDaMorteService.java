package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.CausaDaMorte;
import com.example.osirisgateapi.model.repository.CausaDaMorteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CausaDaMorteService {

    private CausaDaMorteRepository repository;

    public CausaDaMorteService(CausaDaMorteRepository repository){
        this.repository = repository;
    }

    public List<CausaDaMorte> getCausaDaMortes(){
        return repository.findAll();
    }

    public Optional<CausaDaMorte> getCausaDaMorteById(Long id){
        return repository.findById(id);
    }
}
