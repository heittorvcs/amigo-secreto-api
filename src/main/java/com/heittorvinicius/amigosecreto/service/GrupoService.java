package com.heittorvinicius.amigosecreto.service;

import com.heittorvinicius.amigosecreto.model.Grupo;
import com.heittorvinicius.amigosecreto.repository.GrupoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoService {

    private final GrupoRepository repository;

    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    public Grupo criarNovoGrupo(String nome) {
        // TODO: validar se o nome não está vazio
        Grupo novoGrupo = new Grupo(nome);
        return repository.salvar(novoGrupo);
    }

    public List<Grupo> listarGrupos() {
        return repository.listarTodos();
    }
}