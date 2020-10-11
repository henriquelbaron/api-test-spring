package br.com.henrique.teste.dev.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(final String msg) {
        super(msg);
    }
    public UnauthorizedException() {
        super();
    }
}
