package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Familia;
import com.example.osirisgateapi.model.repository.FamiliaRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Familia salvar(Familia familia){
        validar(familia);
        return repository.save(familia);
    }

    @Transactional public void excluir (Familia familia){
        Objects.requireNonNull(familia.getId());
        repository.delete(familia);
    }

    public void validar (Familia familia){
        if (familia.getNomeFamilia() == null || familia.getNomeFamilia().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
