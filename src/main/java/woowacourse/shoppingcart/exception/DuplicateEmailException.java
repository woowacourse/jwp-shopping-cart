package woowacourse.shoppingcart.exception;

public class DuplicateEmailException  extends IllegalRequestException {

    public DuplicateEmailException() {
        super("1001", "이메일이 중복입니다.");
    }
}
