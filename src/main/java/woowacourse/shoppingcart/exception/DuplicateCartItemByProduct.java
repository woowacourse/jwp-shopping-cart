package woowacourse.shoppingcart.exception;

public class DuplicateCartItemByProduct extends RuntimeException {

    public DuplicateCartItemByProduct() {
        this("장바구니에 이미 해당 상품이 존재합니다.");
    }

    public DuplicateCartItemByProduct(final String msg) {
        super(msg);
    }
}
