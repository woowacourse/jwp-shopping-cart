package cart.exception;

public class IllegalPathVariableTypeException extends RuntimeException {
    public IllegalPathVariableTypeException(final String message) {
        super(message);
    }

    public IllegalPathVariableTypeException(final String message, final Throwable exception) {
        super(message, exception);
    }
}
