package woowacourse.shoppingcart.dto.cartitem;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.product.ProductResponse;

public class CartItemResponse {

    private final Long id;
    private final int quantity;
    private final ProductResponse product;

    public CartItemResponse(CartItem cartItem) {
        this.id = cartItem.getId();
        this.quantity = cartItem.getQuantity();
        this.product = new ProductResponse(cartItem.getProduct());
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }
}
