package woowacourse.shoppingcart.domain;

public class Cart {

    private Long id;
    private Long customer_id;
    private Long productId;
    private int quantity;

    public Cart() {
    }

    public Cart(final Long id, final Long customer_id, final Long productId, final int quantity) {
        this.id = id;
        this.customer_id = customer_id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
