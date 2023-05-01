package cart.common;

public class PersistenceException extends RuntimeException {
    public PersistenceException(PersistenceExceptionMessages message) {
        super(message.getMessage());
    }

    public PersistenceException(PersistenceExceptionMessages message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
