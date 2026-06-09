package com.diegoassuncao.barmanager.service;

import com.diegoassuncao.barmanager.dto.ProdutoRequestDTO;
import com.diegoassuncao.barmanager.dto.ProdutoResponseDTO;
import com.diegoassuncao.barmanager.entity.Produto;
import com.diegoassuncao.barmanager.exception.ResourceNotFoundException;
import com.diegoassuncao.barmanager.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoResponseDTO criar(ProdutoRequestDTO produtoRequestDTO){
        Produto produto = Produto.builder()
                .nome(produtoRequestDTO.getNome())
                .descricao(produtoRequestDTO.getDescricao())
                .categoria(produtoRequestDTO.getCategoria())
                .precoCusto(produtoRequestDTO.getPrecoCusto())
                .precoVenda(produtoRequestDTO.getPrecoVenda())
                .quantidade(produtoRequestDTO.getQuantidade())
                .ativo(true)
                .build();

        Produto salvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(
                salvo.getId(),
                salvo.getNome(),
                salvo.getDescricao(),
                salvo.getCategoria(),
                salvo.getPrecoVenda(),
                salvo.getQuantidade(),
                salvo.getAtivo()
        );
    }

    public List<ProdutoResponseDTO> listarTodos(){
        List<Produto> produtosDoBanco = produtoRepository.findAll();
        List <ProdutoResponseDTO> listaDeDtos = new ArrayList<>();
        for(Produto produto : produtosDoBanco){
            ProdutoResponseDTO dto = new ProdutoResponseDTO(
                    produto.getId(),
                    produto.getNome(),
                    produto.getDescricao(),
                    produto.getCategoria(),
                    produto.getPrecoVenda(),
                    produto.getQuantidade(),
                    produto.getAtivo()
            );
            listaDeDtos.add(dto);
        }
        return listaDeDtos;
    }

    public ProdutoResponseDTO buscarPorId(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));

        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getPrecoVenda(),
                produto.getQuantidade(),
                produto.getAtivo()
        );
    }

    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO produtoRequestDTO){
        Produto produtoAtt = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado pelo ID: " + id));

        produtoAtt.setNome(produtoRequestDTO.getNome());
        produtoAtt.setDescricao(produtoRequestDTO.getDescricao());
        produtoAtt.setCategoria(produtoRequestDTO.getCategoria());
        produtoAtt.setPrecoCusto(produtoRequestDTO.getPrecoCusto());
        produtoAtt.setPrecoVenda(produtoRequestDTO.getPrecoVenda());
        produtoAtt.setQuantidade(produtoRequestDTO.getQuantidade());
        produtoRepository.save(produtoAtt);

        return new ProdutoResponseDTO(
                produtoAtt.getId(),
                produtoAtt.getNome(),
                produtoAtt.getDescricao(),
                produtoAtt.getCategoria(),
                produtoAtt.getPrecoVenda(),
                produtoAtt.getQuantidade(),
                produtoAtt.getAtivo()
        );
    }

    public void deletarPorId(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

}
