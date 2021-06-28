package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Lote;
import com.example.osirisgateapi.model.repository.LoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteService {

    private LoteRepository repository;

    public LoteService(LoteRepository repository){
        this.repository = repository;
    }

    public List<Lote> getLotes(){
        return repository.findAll();
    }

    public Optional<Lote> getLoteById(Long id){
        return repository.findById(id);
    }
}
