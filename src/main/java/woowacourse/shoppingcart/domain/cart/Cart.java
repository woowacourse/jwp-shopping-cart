package woowacourse.shoppingcart.domain.cart;

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
