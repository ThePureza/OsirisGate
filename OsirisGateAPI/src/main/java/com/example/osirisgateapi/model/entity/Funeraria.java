package com.example.osirisgateapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funeraria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;//obrigatório
    private String razaoSocial;//obrigatório
    private String cnpj;//obrigatório
    private String email;//obrigatório
    private String telefone;//obrigatório
    private String cep;//obrigatório
    private String logradouro;//obrigatório
    private String numero;//obrigatório
    private String complemento;
    private String bairro;//obrigatório
    private String cidade;//obrigatório
    private String uf;//obrigatório
    private String observacaoFuneraria;

}
