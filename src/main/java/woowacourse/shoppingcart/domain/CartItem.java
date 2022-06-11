package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class CartItem {

    private final long id;
    private final Product product;
    private final int quantity;

    public CartItem(long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(long id, long productId, String name, int price, int stock, String imageUrl, int quantity) {
        this.id = id;
        this.product = new Product(productId, name, price, stock, imageUrl);
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
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
        return id == cartItem.id && quantity == cartItem.quantity && Objects.equals(product, cartItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
