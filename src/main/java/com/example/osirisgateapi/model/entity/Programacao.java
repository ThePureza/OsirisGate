package com.example.osirisgateapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataVelorio;// obrigatório
        private String horaVelorio;// obrigatório
    private String dataSepultamento;// obrigatório
    private String horaSepultamento;// obrigatório
    private String local;// obrigatório
    @ManyToOne
    private Familia familia;// chave estrangeira, obrigatório
    @ManyToOne
    private Falecido falecido;// chave estrangeira, obrigatório

}
