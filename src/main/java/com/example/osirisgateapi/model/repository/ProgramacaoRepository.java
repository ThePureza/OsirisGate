package com.example.osirisgateapi.model.repository;

import com.example.osirisgateapi.model.entity.Programacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramacaoRepository extends JpaRepository<Programacao, Long> {
}
