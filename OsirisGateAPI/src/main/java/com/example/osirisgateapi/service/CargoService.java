package com.example.osirisgateapi.service;

import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
