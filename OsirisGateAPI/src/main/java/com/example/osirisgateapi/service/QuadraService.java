package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Quadra;
import com.example.osirisgateapi.model.repository.QuadraRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuadraService {

    private QuadraRepository repository;

    public QuadraService(QuadraRepository repository){
        this.repository = repository;
    }

    public List<Quadra> getQuadras(){
        return repository.findAll();
    }

    public Optional<Quadra> getQuadraById(Long id){
        return repository.findById(id);
    }
}
