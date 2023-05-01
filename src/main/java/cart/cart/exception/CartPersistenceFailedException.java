package cart.cart.exception;

import cart.common.PersistenceException;
import cart.common.PersistenceExceptionMessages;

public class CartPersistenceFailedException extends PersistenceException {
    public CartPersistenceFailedException(PersistenceExceptionMessages message) {
        super(message);
    }

    public CartPersistenceFailedException(PersistenceExceptionMessages message, Throwable cause) {
        super(message, cause);
    }
}
