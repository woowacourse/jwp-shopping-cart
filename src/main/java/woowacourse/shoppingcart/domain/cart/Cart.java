package woowacourse.shoppingcart.domain.cart;

import woowacourse.shoppingcart.dto.cart.CartSetRequest;

public class Cart {

    private Long id;
    private Long productId;
    private Long customerId;
    private int quantity;

    public Cart() {
    }

    public Cart(Long id, Long productId, Long customerId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

    public Cart(Long productId, Long customerId, int quantity) {
        this(null, productId, customerId, quantity);
    }


    public Cart(Cart cart, CartSetRequest cartSetRequest) {
        this.id = cart.getId();
        this.productId = cart.getProductId();
        this.customerId = cart.getCustomerId();
        this.quantity = cartSetRequest.getQuantity();
    }

    public Cart(Long productId, Long customerId, CartSetRequest cartSetRequest) {
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = cartSetRequest.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public int getQuantity() {
        return quantity;
    }
}
