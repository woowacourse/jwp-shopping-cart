package woowacourse.shoppingcart.domain.cart;

public class CartId {

    private final int id;

    public CartId(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }
}
