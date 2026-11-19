package com.diegoassuncao.barmanager.web.controller;


import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import com.diegoassuncao.barmanager.web.dto.ProdutoRequestDTO;
import com.diegoassuncao.barmanager.web.dto.ProdutoResponseDTO;
import com.diegoassuncao.barmanager.web.dto.ProdutoUpdateRequestDTO;
import com.diegoassuncao.barmanager.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Cadastro e consulta do cardápio")

public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Cadastra um produto")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO){
        ProdutoResponseDTO produtoCriado = produtoService.criar(produtoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @Operation(summary = "Lista produtos (só o ADMIN vê inativos)")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos(@RequestParam(defaultValue = "true") boolean ativo,
                                                                @AuthenticationPrincipal Usuario usuarioLogado){
        List<ProdutoResponseDTO> produtos = produtoService.listarTodos(ativo, usuarioLogado.getPerfil());
        return ResponseEntity.ok(produtos);
    }

    @Operation(summary = "Busca produtos por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id,
                                                          @AuthenticationPrincipal Usuario usuarioLogado){
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarPorId(id, usuarioLogado.getPerfil());
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @Operation(summary = "Busca produtos por nome")
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorNome(@PathVariable String nome,
                                                            @AuthenticationPrincipal Usuario usuarioLogado){
        ProdutoResponseDTO produtoResponseDTO = produtoService.buscarPorNome(nome, usuarioLogado.getPerfil());
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @Operation(summary = "Atualiza produto por ID (parcial)")
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid
    ProdutoUpdateRequestDTO produtoUpdateRequestDTO, @AuthenticationPrincipal Usuario usuarioLogado){
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, produtoUpdateRequestDTO,
                usuarioLogado.getPerfil());
        return ResponseEntity.ok(produtoAtualizado);
    }

    @Operation(summary = "Atualiza produto por nome (parcial)")
    @PutMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> atualizarPorNome(@PathVariable String nome, @RequestBody @Valid
    ProdutoUpdateRequestDTO produtoUpdateRequestDTO, @AuthenticationPrincipal Usuario usuarioLogado){
        ProdutoResponseDTO produtoResponseDTO = produtoService.atualizarPorNome(nome, produtoUpdateRequestDTO,
                usuarioLogado.getPerfil());
        return ResponseEntity.ok(produtoResponseDTO);
    }

    @Operation(summary = "Desativa um produto (Somente ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id){
        produtoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
