package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.dto.UsuarioDTO;
import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.entity.Usuario;
import com.example.osirisgateapi.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id){
        return repository.findById(id);
    }
}
