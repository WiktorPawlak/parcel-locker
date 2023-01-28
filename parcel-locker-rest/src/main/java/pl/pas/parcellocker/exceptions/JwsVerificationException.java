package pl.pas.parcellocker.exceptions;

public class JwsVerificationException extends RuntimeException {

    public JwsVerificationException(final String message) {
        super(message);
    }
}
