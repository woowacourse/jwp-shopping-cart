package woowacourse.shoppingcart.dao.dto;

import java.util.Objects;
import woowacourse.shoppingcart.domain.Product;

public class CartItem {
    private Long Id;
    private Product product;
    private int quantity;

    public CartItem(Long id, Product product, int quantity) {
        Id = id;
        this.product = product;
        this.quantity = quantity;
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
        return quantity == cartItem.quantity && Objects.equals(Id, cartItem.Id) && Objects.equals(
                product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, product, quantity);
    }

    public Long getId() {
        return Id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "Id=" + Id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
