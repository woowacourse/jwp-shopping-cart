package cart.excpetion;

public class ProductionServiceException extends RuntimeException {

    public ProductionServiceException() {
    }

    public ProductionServiceException(final String message) {
        super(message);
    }
}
