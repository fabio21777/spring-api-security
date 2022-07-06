package br.com.alura.forum.security;

import org.springframework.security.core.token.TokenService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoTokenFiltro extends OncePerRequestFilter {
    private final JwtService jwtService;

    public AutenticacaoTokenFiltro(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && !header.isEmpty()) {
            System.out.println("Header: " + header);
           Boolean valido = jwtService.isTokenValido(header);
            System.out.println("---------->"+valido);
        }
        filterChain.doFilter(request, response);
    }
}
