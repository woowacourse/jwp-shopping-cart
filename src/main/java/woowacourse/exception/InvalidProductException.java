package woowacourse.exception;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException() {
        super("존재하지 않는 상품 아이디 입니다.");
    }
}
