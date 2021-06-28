package com.example.osirisgateapi.model.repository;

import com.example.osirisgateapi.model.entity.Ossuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OssuarioRepository extends JpaRepository<Ossuario, Long> {
}
