package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.product.ProductResponse;

public class CartResponse {

    private ProductResponse productResponse;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(ProductResponse productResponse, int quantity) {
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public CartResponse(final Cart cart) {
        this(new ProductResponse(cart.getProduct()), cart.getQuantity());
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public int getQuantity() {
        return quantity;
    }
}
