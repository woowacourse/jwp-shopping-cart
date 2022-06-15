package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Cart {
    private final Long id;
    private final long customerId;
    private final Product product;
    private int quantity;

    public Cart(final Long id, final long customerId, final Product product, final int quantity) {
        this.id = id;
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart(final long customerId, final Product product, final int quantity) {
        this(null, customerId, product, quantity);
    }

    public boolean isSameProductId(final long productId) {
        return product.isSameId(productId);
    }

    public void updateQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public long getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public Long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }

    public long getProductId() {
        return product.getId();
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Cart cart = (Cart) o;
        return customerId == cart.customerId && quantity == cart.quantity && Objects.equals(product,
                cart.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, product, quantity);
    }
}
