package woowacourse.shoppingcart.exception;

public class InvalidCustomerLoginException extends RuntimeException {

    public InvalidCustomerLoginException() {
        this("아이디나 패스워드 정보가 일치하지 않습니다.");
    }

    public InvalidCustomerLoginException(final String msg) {
        super(msg);
    }
}
