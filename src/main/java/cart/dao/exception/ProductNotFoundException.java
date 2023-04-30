package cart.dao.exception;

public final class ProductNotFoundException extends RuntimeException {

    private final String message;

    public ProductNotFoundException(final String message) {
        this.message = message;
    }

    public ProductNotFoundException() {
        this("상품을 찾을 수 없습니다.");
    }

    @Override
    public String getMessage() {
        return message;
    }
}
