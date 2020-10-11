package br.com.henrique.teste.dev.modal.dto;

import lombok.Value;

/**
 * DTO de resposta ao realizar Login
 */
@Value
public class LoginResponseDTO {
    private Boolean autenticado;
    private String login;
    private Boolean administrador;
    private String token;
    private String nome;
}
