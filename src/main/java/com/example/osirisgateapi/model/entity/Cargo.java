package com.example.osirisgateapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCargo;//obrigatório
    private String descricaoCargo;

   // @ManyToMany
   // @JoinTable(name = "cargo_usuario",
    //        joinColumns = @JoinColumn(name = "cargo_id"),
    //        inverseJoinColumns = @JoinColumn(name = "usuario_id"))
   // private List<Usuario> usuarios;

}
