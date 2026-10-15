package com.diegoassuncao.barmanager.infrastructure.repository;

import com.diegoassuncao.barmanager.infrastructure.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNomeUsuarioIgnoreCase(String nomeUsuario);

    boolean existsByNomeUsuarioIgnoreCase(String nomeUsuario);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByDocumento(String documento);

}
