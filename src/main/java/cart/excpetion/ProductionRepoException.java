package cart.excpetion;

public class ProductionRepoException extends RuntimeException {

    public ProductionRepoException() {
    }

    public ProductionRepoException(final String message) {
        super(message);
    }
}
