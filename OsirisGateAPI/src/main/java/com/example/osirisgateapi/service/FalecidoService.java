package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Falecido;
import com.example.osirisgateapi.model.repository.FalecidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FalecidoService {

    private FalecidoRepository repository;

    public FalecidoService(FalecidoRepository repository){
        this.repository = repository;
    }

    public List<Falecido> getFalecidos(){
        return repository.findAll();
    }

    public Optional<Falecido> getFalecidoById(Long id){
        return repository.findById(id);
    }
}
