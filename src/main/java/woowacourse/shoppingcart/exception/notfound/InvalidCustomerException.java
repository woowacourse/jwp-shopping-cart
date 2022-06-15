package woowacourse.shoppingcart.exception.notfound;

public class InvalidCustomerException extends NotFoundException {

    public InvalidCustomerException() {
        super("존재하지 않는 유저입니다.");
    }
}
