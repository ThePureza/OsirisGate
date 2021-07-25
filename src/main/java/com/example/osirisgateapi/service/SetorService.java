package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Setor;
import com.example.osirisgateapi.model.repository.SetorRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public Setor salvar(Setor setor){
        validar(setor);
        return repository.save(setor);
    }

    @Transactional public void excluir (Setor setor){
        Objects.requireNonNull(setor.getId());
        repository.delete(setor);
    }

    public void validar (Setor setor){
        if (setor.getNomeSetor() == null || setor.getNomeSetor().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
