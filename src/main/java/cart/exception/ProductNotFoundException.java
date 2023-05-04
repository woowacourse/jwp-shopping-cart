package cart.exception;

public final class ProductNotFoundException extends GlobalException{

    private static final String message = "해당 상품을 찾을 수 없습니다. 요청 상품 id : %d";

    public ProductNotFoundException(final Long id) {
        super(String.format(message, id));
    }
}
