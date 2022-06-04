package woowacourse.shoppingcart.exception.domain;

public class DuplicateCustomerException extends DuplicateException {

    private static final String ERROR_MESSAGE = "중복된 회원 정보가 있습니다.";

    public DuplicateCustomerException() {
        this(ERROR_MESSAGE);
    }

    public DuplicateCustomerException(String message) {
        super(message);
    }
}
