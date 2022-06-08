package woowacourse.shoppingcart.dto.cart;

public class CartItemCountRequest {

    private int count;

    private CartItemCountRequest() {
    }

    public CartItemCountRequest(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
