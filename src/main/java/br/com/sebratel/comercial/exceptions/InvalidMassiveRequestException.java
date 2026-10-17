package br.com.sebratel.comercial.exceptions;

public class InvalidMassiveRequestException extends RuntimeException {
    public InvalidMassiveRequestException(String message) {
        super(message);
    }
}
