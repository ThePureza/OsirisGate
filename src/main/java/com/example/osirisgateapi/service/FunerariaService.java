package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Funeraria;
import com.example.osirisgateapi.model.repository.FunerariaRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FunerariaService {

    private FunerariaRepository repository;

    public FunerariaService(FunerariaRepository repository){
        this.repository = repository;
    }

    public List<Funeraria> getFunerarias(){
        return repository.findAll();
    }

    public Optional<Funeraria> getFunerariaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Funeraria salvar(Funeraria funeraria){
        validar(funeraria);
        return repository.save(funeraria);
    }

    @Transactional public void excluir (Funeraria funeraria){
        Objects.requireNonNull(funeraria.getId());
        repository.delete(funeraria);
    }

    public void validar (Funeraria funeraria){
        if (funeraria.getNomeFantasia() == null || funeraria.getNomeFantasia().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
