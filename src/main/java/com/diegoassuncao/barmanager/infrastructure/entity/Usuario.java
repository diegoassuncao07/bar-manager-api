package com.diegoassuncao.barmanager.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nomeCompleto;

    @Column(name = "documento", length = 11, nullable = false, unique = true)
    private String documento;

    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String nomeUsuario;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "senha", length = 60, nullable = false)
    private String senha;

    @Column(name = "perfil", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role perfil;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeUsuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
