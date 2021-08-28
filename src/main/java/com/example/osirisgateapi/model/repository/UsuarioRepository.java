package com.example.osirisgateapi.model.repository;

import com.example.osirisgateapi.model.entity.Cargo;
import com.example.osirisgateapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByCargo(Optional<Cargo> cargo);
}
