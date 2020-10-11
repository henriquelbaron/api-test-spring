package br.com.henrique.teste.dev.exception;

public class LoginException extends RuntimeException {
    public LoginException(final String msg) {
        super(msg);
    }
}
