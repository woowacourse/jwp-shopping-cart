package woowacourse.shoppingcart.dto;

import java.util.List;

public class OrderRequest {
   private List<CartRequest> cart;

    public OrderRequest() {
    }

    public OrderRequest(List<CartRequest> cart) {
        this.cart = cart;
    }

    public List<CartRequest> getCart() {
        return cart;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "cart=" + cart +
                '}';
    }
}
