package woowacourse.shoppingcart.dto.response;

import java.util.Objects;
import woowacourse.shoppingcart.domain.CartItem;

public class CartItemDto {

    private ProductDto product;
    private int quantity;

    public CartItemDto() {
    }

    public CartItemDto(CartItem cartItem) {
        this.product = new ProductDto(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        CartItemDto that = (CartItemDto) o;
        return quantity == that.quantity
                && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

    @Override
    public String toString() {
        return "CartItemDto{" + "product=" + product + ", quantity=" + quantity + '}';
    }
}
