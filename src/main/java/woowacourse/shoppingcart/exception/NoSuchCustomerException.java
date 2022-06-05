package woowacourse.shoppingcart.exception;

public class NoSuchCustomerException extends RuntimeException {

    public NoSuchCustomerException() {
        this("등록된 사용자가 없습니다.");
    }

    public NoSuchCustomerException(String msg) {
        super(msg);
    }
}
