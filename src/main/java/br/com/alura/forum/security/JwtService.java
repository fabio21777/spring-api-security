package br.com.alura.forum.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;


    public String gerarToken(Authentication authenticate) {
        Date hoje = new Date();
        Date dataexperacao = new Date(hoje.getTime() + Long.parseLong(expiration));
        Usuario usuario =  (Usuario) authenticate.getPrincipal();
        System.out.println("---------->"+usuario.getEmail());
        System.out.println("---------->"+usuario.getSenha());
        return Jwts.builder()
                .setIssuer("API do Fórum da Alura")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataexperacao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
