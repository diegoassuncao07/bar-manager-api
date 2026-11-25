package com.diegoassuncao.barmanager.service;

import com.diegoassuncao.barmanager.infrastructure.entity.Role;
import com.diegoassuncao.barmanager.web.dto.ProdutoRequestDTO;
import com.diegoassuncao.barmanager.web.dto.ProdutoResponseDTO;
import com.diegoassuncao.barmanager.web.dto.ProdutoUpdateRequestDTO;
import com.diegoassuncao.barmanager.infrastructure.entity.Produto;
import com.diegoassuncao.barmanager.exception.NomeDuplicadoException;
import com.diegoassuncao.barmanager.exception.ResourceNotFoundException;
import com.diegoassuncao.barmanager.infrastructure.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    private static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO produtoRequestDTO) {

        validarDisponibilidadeDoNome(null, produtoRequestDTO.getNome());

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
        log.info("Produto criado. id={}", salvo.getId());
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

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos(boolean ativoSolicitado, Role perfilLogado) {
        boolean ativoEfetivo = definirFiltroAtivo(ativoSolicitado, perfilLogado);
        List<Produto> produtosDoBanco = produtoRepository.findAllByAtivoOrderByIdAsc(ativoEfetivo);
        List<ProdutoResponseDTO> listaDeDtos = new ArrayList<>();
        for (Produto produto : produtosDoBanco) {
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

    private boolean definirFiltroAtivo(boolean ativoSolicitado, Role perfilLogado) {
        return podeVerInativos(perfilLogado) ? ativoSolicitado : true;
    }

    private Produto buscarProdutoVisivelPorId(Long id, Role perfilLogado) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));
        validarVisibilidade(produto, perfilLogado);
        return produto;
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id, Role perfilLogado) {
        Produto produto = buscarProdutoVisivelPorId(id, perfilLogado);

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

    private Produto buscarProdutoVisivelPorNome(String nome, Role perfilLogado) {
        Produto produto = produtoRepository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));
        validarVisibilidade(produto, perfilLogado);
        return produto;
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorNome(String nome, Role perfilLogado) {
        Produto produto = buscarProdutoVisivelPorNome(nome, perfilLogado);

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

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoUpdateRequestDTO produtoUpdateRequestDTO, Role perfilLogado) {
        Produto produtoEntity = buscarProdutoVisivelPorId(id, perfilLogado);

        validarDisponibilidadeDoNome(produtoEntity.getId(), produtoUpdateRequestDTO.getNome());

        produtoEntity.setNome(produtoUpdateRequestDTO.getNome() != null ? produtoUpdateRequestDTO.getNome() : produtoEntity.getNome());
        produtoEntity.setDescricao(produtoUpdateRequestDTO.getDescricao() != null ? produtoUpdateRequestDTO.getDescricao() : produtoEntity.getDescricao());
        produtoEntity.setCategoria(produtoUpdateRequestDTO.getCategoria() != null ? produtoUpdateRequestDTO.getCategoria() : produtoEntity.getCategoria());
        produtoEntity.setPrecoCusto(produtoUpdateRequestDTO.getPrecoCusto() != null ? produtoUpdateRequestDTO.getPrecoCusto() : produtoEntity.getPrecoCusto());
        produtoEntity.setPrecoVenda(produtoUpdateRequestDTO.getPrecoVenda() != null ? produtoUpdateRequestDTO.getPrecoVenda() : produtoEntity.getPrecoVenda());
        produtoEntity.setQuantidade(produtoUpdateRequestDTO.getQuantidade() != null ? produtoUpdateRequestDTO.getQuantidade() : produtoEntity.getQuantidade());
        produtoRepository.save(produtoEntity);
        log.info("Produto atualizado. id={}", produtoEntity.getId());
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

    @Transactional
    public ProdutoResponseDTO atualizarPorNome(String nome, ProdutoUpdateRequestDTO produtoUpdateRequestDTO, Role perfilLogado) {
        Produto produtoEntity = buscarProdutoVisivelPorNome(nome, perfilLogado);

        validarDisponibilidadeDoNome(produtoEntity.getId(), produtoUpdateRequestDTO.getNome());

        produtoEntity.setNome(produtoUpdateRequestDTO.getNome() != null ? produtoUpdateRequestDTO.getNome() : produtoEntity.getNome());
        produtoEntity.setDescricao(produtoUpdateRequestDTO.getDescricao() != null ? produtoUpdateRequestDTO.getDescricao() : produtoEntity.getDescricao());
        produtoEntity.setCategoria(produtoUpdateRequestDTO.getCategoria() != null ? produtoUpdateRequestDTO.getCategoria() : produtoEntity.getCategoria());
        produtoEntity.setPrecoCusto(produtoUpdateRequestDTO.getPrecoCusto() != null ? produtoUpdateRequestDTO.getPrecoCusto() : produtoEntity.getPrecoCusto());
        produtoEntity.setPrecoVenda(produtoUpdateRequestDTO.getPrecoVenda() != null ? produtoUpdateRequestDTO.getPrecoVenda() : produtoEntity.getPrecoVenda());
        produtoEntity.setQuantidade(produtoUpdateRequestDTO.getQuantidade() != null ? produtoUpdateRequestDTO.getQuantidade() : produtoEntity.getQuantidade());
        produtoRepository.save(produtoEntity);
        log.info("Produto atualizado. id={}", produtoEntity.getId());
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

    @Transactional
    public void deletarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO));
        produto.setAtivo(false);
        produtoRepository.save(produto);
        log.info("Produto desativado. id={}", produto.getId());
    }

    private void validarDisponibilidadeDoNome(Long idProdutoSendoEditado, String novoNomeSolicitado) {
        if (novoNomeSolicitado == null) {
            return;
        }
        Optional<Produto> produtoComMesmoNome = produtoRepository.findByNomeIgnoreCase(novoNomeSolicitado);
        if (produtoComMesmoNome.isEmpty()) {
            return;
        }
        if (idProdutoSendoEditado == null || !produtoComMesmoNome.get().getId().equals(idProdutoSendoEditado)) {
            throw new NomeDuplicadoException("Erro de duplicidade: o nome " + novoNomeSolicitado + " já está em uso.");
        }
    }

    private boolean podeVerInativos(Role perfilLogado) {
        return perfilLogado == Role.ADMIN;
    }

    private void validarVisibilidade(Produto produto, Role perfilLogado) {
        if (!produto.getAtivo() && !podeVerInativos(perfilLogado)) {
            throw new ResourceNotFoundException(PRODUTO_NAO_ENCONTRADO);
        }
    }
}
