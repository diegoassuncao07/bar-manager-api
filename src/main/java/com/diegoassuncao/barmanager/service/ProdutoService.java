package com.diegoassuncao.barmanager.service;

import com.diegoassuncao.barmanager.dto.ProdutoRequestDTO;
import com.diegoassuncao.barmanager.dto.ProdutoResponseDTO;
import com.diegoassuncao.barmanager.dto.ProdutoUpdateRequestDTO;
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

    public ProdutoResponseDTO buscarPorNome(String nome){
        Produto produto = produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o nome: " + nome));

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


    public ProdutoResponseDTO atualizar(Long id, ProdutoUpdateRequestDTO produtoUpdateRequestDTO) {
        Produto produtoEntity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado pelo ID: " + id));

        produtoEntity.setNome(produtoUpdateRequestDTO.getNome() != null ? produtoUpdateRequestDTO.getNome() : produtoEntity.getNome());
        produtoEntity.setDescricao(produtoUpdateRequestDTO.getDescricao() != null ? produtoUpdateRequestDTO.getDescricao() : produtoEntity.getDescricao());
        produtoEntity.setCategoria(produtoUpdateRequestDTO.getCategoria() != null ? produtoUpdateRequestDTO.getCategoria() : produtoEntity.getCategoria());
        produtoEntity.setPrecoCusto(produtoUpdateRequestDTO.getPrecoCusto() != null ? produtoUpdateRequestDTO.getPrecoCusto() : produtoEntity.getPrecoCusto());
        produtoEntity.setPrecoVenda(produtoUpdateRequestDTO.getPrecoVenda() != null ? produtoUpdateRequestDTO.getPrecoVenda() : produtoEntity.getPrecoVenda());
        produtoEntity.setQuantidade(produtoUpdateRequestDTO.getQuantidade() != null ? produtoUpdateRequestDTO.getQuantidade() : produtoEntity.getQuantidade());
        produtoRepository.save(produtoEntity);

        return new ProdutoResponseDTO(
                produtoEntity.getId(),
                produtoEntity.getNome(),
                produtoEntity.getDescricao(),
                produtoEntity.getCategoria(),
                produtoEntity.getPrecoVenda(),
                produtoEntity.getQuantidade(),
                produtoEntity.getAtivo()
        );
    }

    public ProdutoResponseDTO atualizarPorNome(String nome, ProdutoUpdateRequestDTO produtoUpdateRequestDTO){
        Produto produtoEntity = produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrando pelo nome: " + nome));

        produtoEntity.setNome(produtoUpdateRequestDTO.getNome() != null ? produtoUpdateRequestDTO.getNome() : produtoEntity.getNome());
        produtoEntity.setDescricao(produtoUpdateRequestDTO.getDescricao() != null ? produtoUpdateRequestDTO.getDescricao() : produtoEntity.getDescricao());
        produtoEntity.setCategoria(produtoUpdateRequestDTO.getCategoria() != null ? produtoUpdateRequestDTO.getCategoria() : produtoEntity.getCategoria());
        produtoEntity.setPrecoCusto(produtoUpdateRequestDTO.getPrecoCusto() != null ? produtoUpdateRequestDTO.getPrecoCusto() : produtoEntity.getPrecoCusto());
        produtoEntity.setPrecoVenda(produtoUpdateRequestDTO.getPrecoVenda() != null ? produtoUpdateRequestDTO.getPrecoVenda() : produtoEntity.getPrecoVenda());
        produtoEntity.setQuantidade(produtoUpdateRequestDTO.getQuantidade() != null ? produtoUpdateRequestDTO.getQuantidade() : produtoEntity.getQuantidade());
        produtoRepository.save(produtoEntity);

        return new ProdutoResponseDTO(
                produtoEntity.getId(),
                produtoEntity.getNome(),
                produtoEntity.getDescricao(),
                produtoEntity.getCategoria(),
                produtoEntity.getPrecoVenda(),
                produtoEntity.getQuantidade(),
                produtoEntity.getAtivo()
        );
    }

    public void deletarPorId(Long id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + id));
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

}
