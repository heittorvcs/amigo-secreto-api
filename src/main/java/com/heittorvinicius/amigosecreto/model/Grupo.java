package com.heittorvinicius.amigosecreto.model;

import lombok.Data; 
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data 
public class Grupo {

    private UUID id;
    private String nome;
    private List<Participante> participantes;

    public Grupo(String nome) {
        this.id = UUID.randomUUID();      
        this.nome = nome;
        this.participantes = new ArrayList<>(); 
    }

    public void adicionarParticipante(Participante participante) {
        this.participantes.add(participante);
    }
}