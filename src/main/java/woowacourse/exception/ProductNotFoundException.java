package woowacourse.exception;

public class ProductNotFoundException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "요청하신 제품이 존재하지 않습니다";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
