package com.example.osirisgateapi.model.repository;

import com.example.osirisgateapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
