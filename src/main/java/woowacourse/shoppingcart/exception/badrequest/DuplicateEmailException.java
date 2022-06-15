package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateEmailException extends DuplicateDomainException {

    public DuplicateEmailException() {
        super("email", "이미 가입된 이메일입니다.");
    }
}
