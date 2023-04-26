package cart.exception.product;

public class NotFoundException extends ProductException {

    private static final String NOT_FOUND_ERROR_MESSAGE = "존재하지 않는 상품입니다.";

    public NotFoundException() {
        super(NOT_FOUND_ERROR_MESSAGE);
    }
}
