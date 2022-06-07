package woowacourse.shoppingcart.exception;

public class DuplicatedProductInCartException extends IllegalRequestException {

    public DuplicatedProductInCartException() {
        super("1101", "중복된 물품입니다.");
    }
}
