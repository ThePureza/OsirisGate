package com.example.osirisgateapi.model.repository;

import com.example.osirisgateapi.model.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Integer>{
    Optional<Credential> findByLogin(String login);
}
