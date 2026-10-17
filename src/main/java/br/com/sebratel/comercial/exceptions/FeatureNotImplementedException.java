package br.com.sebratel.comercial.exceptions;

public class FeatureNotImplementedException extends RuntimeException {
    public FeatureNotImplementedException(String message) {
        super(message);
    }
}