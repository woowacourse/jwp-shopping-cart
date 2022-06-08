package woowacourse.shoppingcart.domain.cartItem;

import java.util.Objects;
import woowacourse.shoppingcart.domain.product.Product;

public class CartItem {

    private final Long id;
    private final Product product;
    private final Quantity quantity;

    private CartItem(Product product, Quantity quantity) {
        this(null, product, quantity);
    }

    private CartItem(Long id, Product product, Quantity quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItem of(Long productId, String name,int price, String imageUrl, String description, int stock, int quantity) {
        return new CartItem(Product.of(productId, name, price, imageUrl, description, stock), new Quantity(quantity));
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public Quantity getQuantity() {
        return quantity;
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
        return quantity == cartItem.quantity && Objects.equals(id, cartItem.id) && Objects.equals(
                product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
