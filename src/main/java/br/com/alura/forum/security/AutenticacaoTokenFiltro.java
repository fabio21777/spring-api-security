package br.com.alura.forum.security;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoTokenFiltro extends OncePerRequestFilter {
    private final JwtService jwtService;

    private final UsuarioRepository usuarioRepository;

    public AutenticacaoTokenFiltro(JwtService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && !header.isEmpty()) {
           Boolean valido = jwtService.isTokenValido(header);

              if(valido){
                autenticarUsuario(request, header);
              }
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(HttpServletRequest request, String header) {
        Usuario usuario = usuarioRepository.findById(jwtService.getIdUsuario(header)).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
