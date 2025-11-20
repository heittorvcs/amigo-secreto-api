package com.heittorvinicius.amigosecreto.model;

import lombok.Data; 
import java.util.UUID;

@Data 
public class Participante {
    private UUID id = UUID.randomUUID();
    private String nome;
    private UUID idAmigoSorteado;

    public Participante(String nome) {
        this.nome = nome;
    }
}