package cart.product.repository;

import cart.common.PersistenceException;
import cart.common.PersistenceExceptionMessages;

public class ProductPersistenceException extends PersistenceException {
    public ProductPersistenceException(PersistenceExceptionMessages message) {
        super(message);
    }

    public ProductPersistenceException(PersistenceExceptionMessages message, Throwable cause) {
        super(message, cause);
    }
}
