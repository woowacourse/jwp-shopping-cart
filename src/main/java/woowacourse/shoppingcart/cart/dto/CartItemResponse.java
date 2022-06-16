package woowacourse.shoppingcart.cart.dto;

import java.util.Objects;
import woowacourse.shoppingcart.cart.domain.CartItem;
import woowacourse.shoppingcart.product.domain.Product;

public class CartItemResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int quantity;

    public CartItemResponse(final CartItem cartItem) {
        final Product product = cartItem.getProduct();

        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.quantity = cartItem.getQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
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
        final CartItemResponse that = (CartItemResponse) o;
        return price == that.price && quantity == that.quantity && Objects.equals(id, that.id)
                && Objects.equals(name, that.name) && Objects.equals(imageUrl, that.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl, quantity);
    }
}
