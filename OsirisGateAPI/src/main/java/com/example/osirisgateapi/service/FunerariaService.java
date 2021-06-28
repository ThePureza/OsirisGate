package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Funeraria;
import com.example.osirisgateapi.model.repository.FunerariaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunerariaService {

    private FunerariaRepository repository;

    public FunerariaService(FunerariaRepository repository){
        this.repository = repository;
    }

    public List<Funeraria> getFunerarias(){
        return repository.findAll();
    }

    public Optional<Funeraria> getFunerariaById(Long id){
        return repository.findById(id);
    }
}
