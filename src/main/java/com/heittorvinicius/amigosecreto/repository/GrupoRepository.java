package com.heittorvinicius.amigosecreto.repository;

import com.heittorvinicius.amigosecreto.model.Grupo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class GrupoRepository {

    private final Map<UUID, Grupo> bancoDeDados = new ConcurrentHashMap<>();

    public Grupo salvar(Grupo grupo) {
        bancoDeDados.put(grupo.getId(), grupo);
        return grupo;
    }

    public List<Grupo> listarTodos() {
        return new ArrayList<>(bancoDeDados.values());
    }
    
    public Grupo buscarPorId(UUID id) {
        return bancoDeDados.get(id);
    }
}