package woowacourse.shoppingcart.exception.badrequest;

public class DuplicateUsernameException extends DuplicateDomainException {

    public DuplicateUsernameException() {
        super("username", "이미 가입된 닉네임입니다.");
    }
}
