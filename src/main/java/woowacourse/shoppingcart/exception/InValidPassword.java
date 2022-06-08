package woowacourse.shoppingcart.exception;

public class InValidPassword extends RuntimeException {

    public InValidPassword() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
