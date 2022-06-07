package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {
    private Long id;
    private ProductResponse product;
    private Integer quantity;

    public CartItemResponse(Long id, ProductResponse product, Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getId(), new ProductResponse(cartItem.getProduct()), cartItem.getQuantity());
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

    @Override
    public String toString() {
        return "CartItemResponse{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
