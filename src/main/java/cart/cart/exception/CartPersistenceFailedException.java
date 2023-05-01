package cart.cart.exception;

import cart.config.PersistenceException;

public class CartPersistenceFailedException extends PersistenceException {
    public CartPersistenceFailedException(String message) {
        super(message);
    }

    public CartPersistenceFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
