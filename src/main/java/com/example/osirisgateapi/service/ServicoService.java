package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Servico;
import com.example.osirisgateapi.model.repository.ServicoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class ServicoService {

    private ServicoRepository repository;

    public ServicoService(ServicoRepository repository){
        this.repository = repository;
    }

    public List<Servico> getServicos(){
        return repository.findAll();
    }

    public Optional<Servico> getServicoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Servico salvar(Servico servico){
        validar(servico);
        return repository.save(servico);
    }

    @Transactional public void excluir (Servico servico){
        Objects.requireNonNull(servico.getId());
        repository.delete(servico);
    }

    public void validar (Servico servico){
        if (servico.getNomeServico() == null || servico.getNomeServico().trim().equals("")){
            throw new RegraNegocioException("Serviço inválido");
        }
    }
}
