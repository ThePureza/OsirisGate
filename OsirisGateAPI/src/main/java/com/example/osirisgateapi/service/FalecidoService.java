package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Falecido;
import com.example.osirisgateapi.model.repository.FalecidoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FalecidoService {

    private FalecidoRepository repository;

    public FalecidoService(FalecidoRepository repository){
        this.repository = repository;
    }

    public List<Falecido> getFalecidos(){
        return repository.findAll();
    }

    public Optional<Falecido> getFalecidoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Falecido salvar(Falecido falecido){
        validar(falecido);
        return repository.save(falecido);
    }

    @Transactional public void excluir (Falecido falecido){
        Objects.requireNonNull(falecido.getId());
        repository.delete(falecido);
    }

    public void validar (Falecido falecido){
        if (falecido.getNomeFalecido() == null || falecido.getNomeFalecido().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
        if (falecido.getFamilia() == null || falecido.getFamilia().getId() == null || falecido.getFamilia().getId() == 0){
            throw new RegraNegocioException("Família inválida");
        }
        if (falecido.getFuneraria() == null || falecido.getFuneraria().getId() == null || falecido.getFuneraria().getId() == 0){
            throw new RegraNegocioException("Funerária inválida");
        }
        if (falecido.getCausaDaMorte() == null || falecido.getCausaDaMorte().getId() == null || falecido.getCausaDaMorte().getId() == 0){
            throw new RegraNegocioException("Causa da morte inválida");
        }
        if (falecido.getServico() == null || falecido.getServico().getId() == null || falecido.getServico().getId() == 0){
            throw new RegraNegocioException("Serviço inválido");
        }
        if (falecido.getSetor() == null || falecido.getSetor().getId() == null || falecido.getSetor().getId() == 0){
            throw new RegraNegocioException("Setor inválido");
        }
        if (falecido.getQuadra() == null || falecido.getQuadra().getId() == null || falecido.getQuadra().getId() == 0){
            throw new RegraNegocioException("Quadra inválida");
        }
    }
}
