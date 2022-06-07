package woowacourse.shoppingcart.exception.duplicate;

public class DuplicateCustomerException extends DuplicateException {

    public DuplicateCustomerException() {
        super("이미 존재하는 사용자입니다😅");
    }
}
