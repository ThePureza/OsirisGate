package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Programacao;
import com.example.osirisgateapi.model.repository.ProgramacaoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProgramacaoService {

    private ProgramacaoRepository repository;

    public ProgramacaoService(ProgramacaoRepository repository){
        this.repository = repository;
    }

    public List<Programacao> getProgramacoes(){
        return repository.findAll();
    }

    public Optional<Programacao> getProgramacaoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Programacao salvar(Programacao programacao){
        validar(programacao);
        return repository.save(programacao);
    }

    @Transactional public void excluir (Programacao programacao){
        Objects.requireNonNull(programacao.getId());
        repository.delete(programacao);
    }

    public void validar (Programacao programacao){
        if (programacao.getDataVelorio() == null || programacao.getDataVelorio().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if (programacao.getFamilia() == null || programacao.getFamilia().getId() == null || programacao.getFamilia().getId() == 0){
            throw new RegraNegocioException("Cargo inválido");
        }
        if (programacao.getFalecido() == null || programacao.getFalecido().getId() == null || programacao.getFalecido().getId() == 0){
            throw new RegraNegocioException("Cargo inválido");
        }
    }
}
