package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Cart {

    private Long id;
    private Product product;
    private Quantity quantity;

    public Cart(final Long id, final Product product, final int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public int getQuantity() {
        return quantity.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
