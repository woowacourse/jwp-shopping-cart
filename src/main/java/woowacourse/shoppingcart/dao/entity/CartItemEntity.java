package woowacourse.shoppingcart.dao.entity;

import woowacourse.shoppingcart.domain.cart.Cart;

public class CartItemEntity {

    private final Long id;
    private final Long customerId;
    private final Long productId;

    public CartItemEntity(Long id, Long customerId, Long productId) {
        this.id = id;
        this.customerId = customerId;
        this.productId = productId;
    }

    public CartItemEntity(Long customerId, Long productId) {
        this(null, customerId, productId);
    }

    public static CartItemEntity from(Cart cart) {
        return new CartItemEntity(cart.getId(), cart.getCustomerId(), cart.getProduct().getId());
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }
}
