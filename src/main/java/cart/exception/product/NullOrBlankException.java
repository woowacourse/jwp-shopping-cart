package cart.exception.product;

public class NullOrBlankException extends ProductException {

    private static final String NULL_OR_BLANCK_ERROR_MESSAGE = "상품의 필드 값은 null이나 빈 값이 될 수 없습니다.";

    public NullOrBlankException() {
        super(NULL_OR_BLANCK_ERROR_MESSAGE);
    }
}
