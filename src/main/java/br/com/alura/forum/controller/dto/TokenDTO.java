package br.com.alura.forum.controller.dto;

public class TokenDTO {
    private final String token;
    private final String tipo;

    public TokenDTO(String token, String bearer) {
        this.token = token;
        this.tipo = bearer;
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
