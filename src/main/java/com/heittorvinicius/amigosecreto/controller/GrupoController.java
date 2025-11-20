package com.heittorvinicius.amigosecreto.controller;

import com.heittorvinicius.amigosecreto.model.Grupo;
import com.heittorvinicius.amigosecreto.service.GrupoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}