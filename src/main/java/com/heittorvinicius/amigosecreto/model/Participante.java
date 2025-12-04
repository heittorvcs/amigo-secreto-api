package com.heittorvinicius.amigosecreto.model;

import lombok.Data; 
import java.util.UUID;

@Data 
public class Participante {
    private UUID id = UUID.randomUUID();
    private String nome;
    private String email;
    private UUID idAmigoSorteado;

    public Participante(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}