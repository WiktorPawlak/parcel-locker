package pl.pas.parcellocker.exceptions;

public class PermissionValidationException extends RuntimeException {

    public PermissionValidationException(final String message) {
        super(message);
    }
}
