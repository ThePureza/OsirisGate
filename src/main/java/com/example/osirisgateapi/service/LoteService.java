package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Lote;
import com.example.osirisgateapi.model.repository.LoteRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoteService {

    private LoteRepository repository;

    public LoteService(LoteRepository repository){
        this.repository = repository;
    }

    public List<Lote> getLotes(){
        return repository.findAll();
    }

    public Optional<Lote> getLoteById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Lote salvar(Lote lote){
        validar(lote);
        return repository.save(lote);
    }

    @Transactional public void excluir (Lote lote){
        Objects.requireNonNull(lote.getId());
        repository.delete(lote);
    }

    public void validar (Lote lote){
        if (lote.getTipoDeLote() == null || lote.getTipoDeLote().trim().equals("")){
            throw new RegraNegocioException("Tipo de lote inválido");
        }
        if (lote.getSetor() == null || lote.getSetor().getId() == null || lote.getSetor().getId() == 0){
            throw new RegraNegocioException("Setor inválido");
        }
        if (lote.getQuadra() == null || lote.getQuadra().getId() == null || lote.getQuadra().getId() == 0){
            throw new RegraNegocioException("Quadra inválida");
        }
    }
}
