package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Ossuario;
import com.example.osirisgateapi.model.repository.OssuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OssuarioService {

    private OssuarioRepository repository;

    public OssuarioService(OssuarioRepository repository){
        this.repository = repository;
    }

    public List<Ossuario> getOssuarios(){
        return repository.findAll();
    }

    public Optional<Ossuario> getOssuarioById(Long id){
        return repository.findById(id);
    }
}
