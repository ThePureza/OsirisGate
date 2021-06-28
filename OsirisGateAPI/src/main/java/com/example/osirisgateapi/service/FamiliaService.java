package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Familia;
import com.example.osirisgateapi.model.repository.FamiliaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamiliaService {

    private FamiliaRepository repository;

    public FamiliaService(FamiliaRepository repository){
        this.repository = repository;
    }

    public List<Familia> getFamilias(){
        return repository.findAll();
    }

    public Optional<Familia> getFamiliaById(Long id){
        return repository.findById(id);
    }
}
