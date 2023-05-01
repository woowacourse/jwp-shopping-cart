package cart.product.repository;

import cart.config.PersistenceException;

public class ProductPersistenceException extends PersistenceException {
    public ProductPersistenceException(String message) {
        super(message);
    }

    public ProductPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
