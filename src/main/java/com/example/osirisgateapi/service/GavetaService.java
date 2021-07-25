package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Gaveta;
import com.example.osirisgateapi.model.repository.GavetaRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Gaveta salvar(Gaveta gaveta){
        validar(gaveta);
        return repository.save(gaveta);
    }

    @Transactional public void excluir (Gaveta gaveta){
        Objects.requireNonNull(gaveta.getId());
        repository.delete(gaveta);
    }

    public void validar (Gaveta gaveta){
        if (gaveta.getNumeroGaveta() == null || gaveta.getNumeroGaveta().trim().equals("")){
            throw new RegraNegocioException("Gaveta inválida");
        }
        if (gaveta.getOssuario() == null || gaveta.getOssuario().getId() == null || gaveta.getOssuario().getId() == 0){
            throw new RegraNegocioException("Ossuário inválido");
        }
    }
}
