package woowacourse.shoppingcart.exception.notfound;

public class InvalidCustomerException extends BadRequestException {

    public InvalidCustomerException() {
        super("존재하지 않는 유저입니다.");
    }
}
