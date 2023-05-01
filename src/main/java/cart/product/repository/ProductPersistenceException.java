package cart.product.repository;

import cart.common.exception.PersistenceException;
import cart.common.exception.PersistenceExceptionMessages;

public class ProductPersistenceException extends PersistenceException {
    public ProductPersistenceException(PersistenceExceptionMessages message) {
        super(message);
    }

    public ProductPersistenceException(PersistenceExceptionMessages message, Throwable cause) {
        super(message, cause);
    }
}
