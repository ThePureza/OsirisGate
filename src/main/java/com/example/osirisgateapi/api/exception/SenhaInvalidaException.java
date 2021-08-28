package com.example.osirisgateapi.api.exception;

public class SenhaInvalidaException extends RuntimeException{

    public SenhaInvalidaException() {
        super("Senha inválida");
    }
}
