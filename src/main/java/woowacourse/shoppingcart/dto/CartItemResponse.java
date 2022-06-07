package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.product.ProductResponse;

public class CartItemResponse {

    private final Long id;
    private final ProductResponse product;
    private final Integer quantity;

    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getId(), new ProductResponse(cartItem.getProduct()), cartItem.getQuantity());
    }

    public CartItemResponse(Long id, ProductResponse product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
