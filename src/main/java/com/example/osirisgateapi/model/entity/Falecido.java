package com.example.osirisgateapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Falecido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Familia familia; // chave estrangeira, obrigatório
    @ManyToOne
    private Funeraria funeraria;// chave estrangeira, obrigatório
    private String nomeFalecido;//obrigatório
    private String cpf;//obrigatório
    private String rg;//obrigatório
    private String sexo;//obrigatório
    private String dataNascimento;
    private String nomeMae;
    private String nomePai;
    private String dataMorte;//obrigatório
    private String dataSepultamento;//obrigatório
    private String estadoCivil;//obrigatório
    private String certidaoObito;//obrigatório
    private String medico;//obrigatório
    @ManyToOne
    private CausaDaMorte causaDaMorte;//chave estrangeira, obrigatório
    private String naturalidade;//obrigatório
    private String cep;//obrigatório
    private String logradouro;//obrigatório
    private String numero;//obrigatório
    private String complemento;
    private String bairro;//obrigatório
    private String cidade;//obrigatório
    private String uf;//obrigatório
    @OneToOne(cascade = CascadeType.ALL)
    private Servico servico;// chave estrangeira, obrigatório
    private String prestadorServico;//obrigatório
    @ManyToOne
    private Setor setor;
    @ManyToOne
    private Quadra quadra;// chave estrangeira
    private String observacao;



}
