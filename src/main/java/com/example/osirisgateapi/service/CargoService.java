package com.example.osirisgateapi.service;

import com.example.osirisgateapi.api.exception.RegraNegocioException;
import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.repository.CargoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class CargoService {

    private CargoRepository repository;

    public CargoService(CargoRepository repository){
        this.repository = repository;
    }

    public List<Cargo> getCargos(){
        return repository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cargo salvar(Cargo cargo){
        validar(cargo);
        return repository.save(cargo);
    }

    @Transactional public void excluir (Cargo cargo){
        Objects.requireNonNull(cargo.getId());
        repository.delete(cargo);
    }

    public void validar (Cargo cargo){
        if (cargo.getNomeCargo() == null || cargo.getNomeCargo().trim().equals("")){
            throw new RegraNegocioException("Nome inválido");
        }
    }
}
