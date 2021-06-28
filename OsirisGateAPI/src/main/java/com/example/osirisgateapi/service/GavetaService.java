package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Gaveta;
import com.example.osirisgateapi.model.repository.GavetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GavetaService {

    private GavetaRepository repository;

    public GavetaService(GavetaRepository repository){
        this.repository = repository;
    }

    public List<Gaveta> getGavetas(){
        return repository.findAll();
    }

    public Optional<Gaveta> getGavetaById(Long id){
        return repository.findById(id);
    }
}
