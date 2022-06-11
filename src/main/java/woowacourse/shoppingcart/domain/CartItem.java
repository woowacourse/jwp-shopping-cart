package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {
    private final Product product;
    private Quantity quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public void changeQuantity(int quantity) {
        this.quantity = new Quantity(quantity);
    }

    OrderDetail checkOut() {
        return new OrderDetail(product, quantity);
    }

    boolean isProductId(Long id) {
        return product.isId(id);
    }

    public Product getProduct() {
        return product;
    }

    public Long getProductId() {
        return product.getId();
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
        CartItem cartItem = (CartItem) o;
        return Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
