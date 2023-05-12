package cart.exception;

public class ForbiddenException extends RuntimeException {

    private final String message;

    public ForbiddenException(final String message) {
        this.message = message;
    }
}
