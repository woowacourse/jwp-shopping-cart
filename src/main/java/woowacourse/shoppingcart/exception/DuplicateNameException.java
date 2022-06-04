package woowacourse.shoppingcart.exception;

public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super("이미 존재하는 이름입니다.");
    }
}
