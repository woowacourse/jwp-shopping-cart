package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.product.ProductFindResponse;

public class CartItemResponse {

    private final Long id;
    private final ProductFindResponse product;
    private final Integer quantity;

    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getId(), new ProductFindResponse(cartItem.getProduct()), cartItem.getQuantity());
    }

    public CartItemResponse(Long id, ProductFindResponse product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public ProductFindResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
