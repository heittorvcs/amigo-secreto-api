package com.heittorvinicius.amigosecreto.service;

import com.heittorvinicius.amigosecreto.model.Grupo;
import com.heittorvinicius.amigosecreto.model.Participante;
import com.heittorvinicius.amigosecreto.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;

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

  
    public Grupo adicionarParticipante(UUID idGrupo, String nomeParticipante, String emailParticipante) {
        Grupo grupo = repository.buscarPorId(idGrupo);

        if (grupo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado");
        }

        if (grupo.isSorteado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este grupo já foi sorteado. Não é possível adicionar novos membros.");
        }

        Participante novoParticipante = new Participante(nomeParticipante, emailParticipante);
        grupo.adicionarParticipante(novoParticipante);

        return grupo;
    }

    public void realizarSorteio(UUID idGrupo) {
        Grupo grupo = repository.buscarPorId(idGrupo);

        if (grupo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado");
        }

        if (grupo.isSorteado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O sorteio deste grupo já foi realizado.");
        }

        if (grupo.getParticipantes().size() < 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "É necessário ter no mínimo 3 participantes para realizar o sorteio.");
        }

        Collections.shuffle(grupo.getParticipantes());

        int totalParticipantes = grupo.getParticipantes().size();

        for (int i = 0; i < totalParticipantes; i++) {
            Participante quemDa = grupo.getParticipantes().get(i);

            int indiceQuemRecebe = (i + 1) % totalParticipantes;
            Participante quemRecebe = grupo.getParticipantes().get(indiceQuemRecebe);

            quemDa.setIdAmigoSorteado(quemRecebe.getId());

            System.out.println("Sorteio: " + quemDa.getNome() + " tirou " + quemRecebe.getNome());
        }

        grupo.setSorteado(true);
    }

    public String consultarAmigoSecreto(UUID idGrupo, UUID idParticipante) {
        Grupo grupo = repository.buscarPorId(idGrupo);
        if (grupo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado.");
        }

        if (!grupo.isSorteado()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O sorteio ainda não foi realizado. Aguarde todos se cadastrarem!");
        }

        Participante participante = grupo.getParticipantes().stream()
                .filter(p -> p.getId().equals(idParticipante))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Participante não encontrado neste grupo."));

        if (participante.getIdAmigoSorteado() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de consistência: Amigo não atribuído.");
        }

        Participante amigoSorteado = grupo.getParticipantes().stream()
                .filter(p -> p.getId().equals(participante.getIdAmigoSorteado()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Amigo sorteado não encontrado na lista."));

        return amigoSorteado.getNome();
    }
}