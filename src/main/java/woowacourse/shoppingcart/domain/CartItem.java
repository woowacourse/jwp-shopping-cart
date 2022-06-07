package woowacourse.shoppingcart.domain;

public class CartItem {
    private Long id;
    private Long customerId;
    private Product product;
    private Integer quantity;

    public CartItem() {
    }

    public CartItem(Long id, Long customerId, Product product, Integer quantity) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(Long customerId, Product product, Integer quantity) {
        this(null, customerId, product, quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
