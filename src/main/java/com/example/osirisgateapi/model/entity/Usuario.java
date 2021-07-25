package com.example.osirisgateapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeUsuario;//obrigatório
    private String cpf;//obrigatório
    private String rg;//obrigatório
    private String sexo;//obrigatório
    private String dataNascimento;//obrigatório
    private String email;//obrigatório
    private String telefone;//obrigatório
    private String cep;//obrigatório
    private String logradouro;//obrigatório
    private String numero;//obrigatório
    private String complemento;
    private String bairro;//obrigatório
    private String cidade;//obrigatório
    private String uf;//obrigatório

    @ManyToOne
    private Cargo cargo;// chave estrangeira, obrigatório
}
