package cart.cart.exception;

import cart.common.exception.PersistenceException;
import cart.common.exception.PersistenceExceptionMessages;

public class CartPersistenceFailedException extends PersistenceException {
    public CartPersistenceFailedException(PersistenceExceptionMessages message) {
        super(message);
    }

    public CartPersistenceFailedException(PersistenceExceptionMessages message, Throwable cause) {
        super(message, cause);
    }
}
