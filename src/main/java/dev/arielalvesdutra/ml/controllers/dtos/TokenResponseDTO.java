package dev.arielalvesdutra.ml.controllers.dtos;

public class TokenResponseDTO {

    private String token;

    public TokenResponseDTO() {
    }

    public TokenResponseDTO(String token) {
        setToken(token);
    }

    public String getToken() {
        return token;
    }

    public TokenResponseDTO setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public String toString() {
        return "TokenResponseDTO{" +
                "token='" + token + '\'' +
                '}';
    }
}
