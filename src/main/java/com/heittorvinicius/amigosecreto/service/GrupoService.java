package com.heittorvinicius.amigosecreto.service;

import com.heittorvinicius.amigosecreto.model.Grupo;
import com.heittorvinicius.amigosecreto.model.Participante;
import com.heittorvinicius.amigosecreto.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class GrupoService {

    private final GrupoRepository repository;

    public GrupoService(GrupoRepository repository) {
        this.repository = repository;
    }

    public Grupo criarNovoGrupo(String nome) {
        Grupo novoGrupo = new Grupo(nome);
        return repository.salvar(novoGrupo);
    }

    public List<Grupo> listarGrupos() {
        return repository.listarTodos();
    }

  
    public Grupo adicionarParticipante(UUID idGrupo, String nomeParticipante) {
        Grupo grupo = repository.buscarPorId(idGrupo);

        if (grupo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado");
        }

        if (grupo.isSorteado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este grupo já foi sorteado. Não é possível adicionar novos membros.");
        }

        Participante novoParticipante = new Participante(nomeParticipante);
        grupo.adicionarParticipante(novoParticipante);

        return grupo;
    }
}