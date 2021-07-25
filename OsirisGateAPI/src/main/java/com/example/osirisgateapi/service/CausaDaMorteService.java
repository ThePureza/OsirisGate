package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.CausaDaMorte;
import com.example.osirisgateapi.model.repository.CausaDaMorteRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CausaDaMorteService {

    private CausaDaMorteRepository repository;

    public CausaDaMorteService(CausaDaMorteRepository repository){
        this.repository = repository;
    }

    public List<CausaDaMorte> getCausaDaMortes(){
        return repository.findAll();
    }

    public Optional<CausaDaMorte> getCausaDaMorteById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public CausaDaMorte salvar(CausaDaMorte causaDaMorte){
        validar(causaDaMorte);
        return repository.save(causaDaMorte);
    }

    @Transactional public void excluir (CausaDaMorte causaDaMorte){
        Objects.requireNonNull(causaDaMorte.getId());
        repository.delete(causaDaMorte);
    }

    public void validar (CausaDaMorte causaDaMorte){
        if (causaDaMorte.getNomeCausaDaMorte() == null || causaDaMorte.getNomeCausaDaMorte().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
