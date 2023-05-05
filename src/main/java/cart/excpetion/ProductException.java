package cart.excpetion;

public class ProductException extends RuntimeException {

    public ProductException() {
    }

    public ProductException(final String message) {
        super(message);
    }
}
