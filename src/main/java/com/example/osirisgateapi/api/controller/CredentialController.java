package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CredenciaisDTO;
import com.example.osirisgateapi.api.dto.TokenDTO;
import com.example.osirisgateapi.api.exception.SenhaInvalidaException;
import com.example.osirisgateapi.model.entity.Credential;
import com.example.osirisgateapi.security.JwtService;
import com.example.osirisgateapi.service.CredentialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/credentials")
@RequiredArgsConstructor
@Api("API de Credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ApiOperation("Criar um usuário sistema")
    @ResponseStatus(HttpStatus.CREATED)
    public Credential salvar(@RequestBody Credential credential ){
        String senhaCriptografada = passwordEncoder.encode(credential.getSenha());
        credential.setSenha(senhaCriptografada);
        return credentialService.salvar(credential);
    }

    @PostMapping("/auth")
    @ApiOperation("Token da senha do usuário sistema")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Credential credential = Credential.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails credentialAutenticado = credentialService.autenticar(credential);
            String token = jwtService.gerarToken(credential);
            return new TokenDTO(credential.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}

