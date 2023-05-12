package cart.excpetion.product;

public class DuplicateProductException extends ProductException {
    public DuplicateProductException(final String message) {
        super(message);
    }
}
