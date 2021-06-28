package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Setor;
import com.example.osirisgateapi.model.repository.SetorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SetorService {

    private SetorRepository repository;

    public SetorService(SetorRepository repository){
        this.repository = repository;
    }

    public List<Setor> getSetores(){
        return repository.findAll();
    }

    public Optional<Setor> getSetorById(Long id){
        return repository.findById(id);
    }
}
