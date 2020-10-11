package br.com.henrique.teste.dev.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Classe criada para centralizar utilização das properties
 */
@Configuration
@PropertySource("classpath:application.properties")
public class Properties {
    @Value("${mensagem.idNaoEncontrado}")
    public String idNaoEncontrado;

    @Value("${mensagem.loginOuSenhaInvalido}")
    public String loginOuSenhaInvalido;

    @Value("${mensagem.tokenExpirado}")
    public String tokenExpirado;

    @Value("${config.minutoValidadeToken}")
    public Long minutosValidadeToken;
}
