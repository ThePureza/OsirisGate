package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Quadra;
import com.example.osirisgateapi.model.repository.QuadraRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuadraService {

    private QuadraRepository repository;

    public QuadraService(QuadraRepository repository){
        this.repository = repository;
    }

    public List<Quadra> getQuadras(){
        return repository.findAll();
    }

    public Optional<Quadra> getQuadraById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Quadra salvar(Quadra quadra){
        validar(quadra);
        return repository.save(quadra);
    }

    @Transactional public void excluir (Quadra quadra){
        Objects.requireNonNull(quadra.getId());
        repository.delete(quadra);
    }

    public void validar (Quadra quadra){
        if (quadra.getNumeroQuadra() == null || quadra.getNumeroQuadra().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if (quadra.getSetor() == null || quadra.getSetor().getId() == null || quadra.getSetor().getId() == 0){
            throw new RegraNegocioException("Setor inválido");
        }
    }
}
