package cart.excpetion.product;

public class InvalidProductException extends ProductException {
    public InvalidProductException(final String message) {
        super(message);
    }
}
