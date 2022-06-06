package woowacourse.shoppingcart.cart.domain;

import java.util.Objects;
import woowacourse.shoppingcart.exception.badrequest.InvalidQuantityException;
import woowacourse.shoppingcart.product.domain.Product;

public class Cart {

    private static final int MIN_QUANTITY = 1;

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    private Cart() {
    }

    public Cart(final Long id, final Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), MIN_QUANTITY);
    }

    public Cart(final Long id, final Long productId, final String name, final int price, final String imageUrl,
                final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Cart changeQuantity(final int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new InvalidQuantityException();
        }
        return new Cart(id, productId, name, price, imageUrl, quantity);
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
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
        final Cart cart = (Cart) o;
        return price == cart.price && quantity == cart.quantity && Objects.equals(id, cart.id)
                && Objects.equals(productId, cart.productId) && Objects.equals(name, cart.name)
                && Objects.equals(imageUrl, cart.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, price, imageUrl, quantity);
    }
}
