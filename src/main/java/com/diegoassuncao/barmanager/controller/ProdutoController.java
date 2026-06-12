package com.diegoassuncao.barmanager.controller;


import com.diegoassuncao.barmanager.dto.ProdutoRequestDTO;
import com.diegoassuncao.barmanager.dto.ProdutoResponseDTO;
import com.diegoassuncao.barmanager.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor

public class ProdutoController {
    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoCriado = produtoService.criar(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos(){
        List<ProdutoResponseDTO> produtos = produtoService.listarTodos();
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id){
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorNome(@PathVariable String nome){
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody
    ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, produtoRequestDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PutMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> atualizarPorNome(@PathVariable String nome, @RequestBody @Valid
    ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoResponseDTO = produtoService.atualizarPorNome(nome, produtoRequestDTO);
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        produtoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
