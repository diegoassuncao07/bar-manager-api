package com.diegoassuncao.barmanager.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authorizationHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        // Se o usuário existe E ainda não está autenticado no "bolso" do Spring
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 1. Usa o "Buscador" para pegar o usuário do banco
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 2. Se o token for válido
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 3. Cria o "crachá" (Authentication)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities() // As PERMISSÕES (do User.java)
                );

                // 4. COLOCA o "crachá" no "bolso" (SecurityContext)
                // O Spring agora sabe que este usuário está autenticado
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response); // Passa para o próximo filtro

    }

}
