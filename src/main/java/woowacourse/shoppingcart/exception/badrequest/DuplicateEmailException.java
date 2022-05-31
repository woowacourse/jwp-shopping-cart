package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateEmailException extends NotFoundException {

    public DuplicateEmailException() {
        super("1001", "이메일이 중복입니다.");
    }
}
