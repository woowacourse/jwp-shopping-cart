package woowacourse.shoppingcart.exception;

public class DuplicatedCartProductException extends RuntimeException {

    public DuplicatedCartProductException() {
        this("이미 장바구니에 존재하는 상품입니다.");
    }

    public DuplicatedCartProductException(final String msg) {
        super(msg);
    }
}
