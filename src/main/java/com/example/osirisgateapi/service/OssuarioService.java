package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Ossuario;
import com.example.osirisgateapi.model.repository.OssuarioRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Ossuario salvar(Ossuario ossuario){
        validar(ossuario);
        return repository.save(ossuario);
    }

    @Transactional public void excluir (Ossuario ossuario){
        Objects.requireNonNull(ossuario.getId());
        repository.delete(ossuario);
    }

    public void validar (Ossuario ossuario){
        if (ossuario.getNomeOssuario() == null || ossuario.getNomeOssuario().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
