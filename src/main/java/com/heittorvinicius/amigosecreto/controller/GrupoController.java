package com.heittorvinicius.amigosecreto.controller;

import com.heittorvinicius.amigosecreto.model.ParticipanteInput;
import com.heittorvinicius.amigosecreto.model.Grupo;
import com.heittorvinicius.amigosecreto.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private final GrupoService service;

    public GrupoController(GrupoService service) {
        this.service = service;
    }

    @PostMapping
    public Grupo criar(@RequestBody String nome) {
        return service.criarNovoGrupo(nome);
    }

    @GetMapping
    public List<Grupo> listar() {
        return service.listarGrupos();
    }

    @Operation(summary = "Adicionar Participante", description = "Adiciona uma pessoa ao grupo, caso o sorteio ainda não tenha ocorrido.")
    @PostMapping("/{id}/participantes")
    public ResponseEntity<Grupo> adicionarParticipante(
            @PathVariable UUID id, 
            @RequestBody ParticipanteInput input) {
        
        Grupo grupoAtualizado = service.adicionarParticipante(id, input.nome());
        return ResponseEntity.ok(grupoAtualizado);
    }
}