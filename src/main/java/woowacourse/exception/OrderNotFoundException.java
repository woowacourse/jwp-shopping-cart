package woowacourse.exception;

public class OrderNotFoundException extends IllegalArgumentException {
    private static final String DEFAULT_MESSAGE = "주문 조회에 실패했습니다";

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
