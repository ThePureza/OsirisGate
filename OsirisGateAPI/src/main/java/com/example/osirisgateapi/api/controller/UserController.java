package com.example.osirisgateapi.api.controller;

import com.example.osirisgateapi.api.dto.CredenciaisDTO;
import com.example.osirisgateapi.api.dto.TokenDTO;
import com.example.osirisgateapi.api.exception.SenhaInvalidaException;
import com.example.osirisgateapi.model.entity.User;
import com.example.osirisgateapi.security.JwtService;
import com.example.osirisgateapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User salvar(@RequestBody User user){
        String senhaCriptografada = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCriptografada);
        return userService.salvar(user);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            User user = User.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails userAutenticado = userService.autenticar(user);
            String token = jwtService.gerarToken(user);
            return new TokenDTO(user.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
