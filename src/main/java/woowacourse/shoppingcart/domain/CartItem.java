package woowacourse.shoppingcart.domain;

import static lombok.EqualsAndHashCode.*;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class CartItem {

    @Include
    private final Long id;
    private final Product product;
    private final Quantity quantity;

    public CartItem(Product product, int quantity) {
        this(null, product, quantity);
    }

    @Builder
    public CartItem(Long id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = new Quantity(quantity);
    }

    public CartItem createWithId(Long id) {
        return new CartItem(id, product, quantity.getValue());
    }

    public boolean isSameProductId(Long productId) {
        return this.product.isSameId(productId);
    }

    public Long getProductId() {
        return this.product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public String getImageUrl() {
        return product.getImageUrl();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public int getQuantity() {
        return quantity.getValue();
    }
}
