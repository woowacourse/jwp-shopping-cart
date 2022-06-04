package woowacourse.shoppingcart.exception;

public class DuplicatedCustomerEmailException extends IllegalArgumentException {

    public DuplicatedCustomerEmailException() {
        super("중복된 이메일 입니다.");
    }
}
