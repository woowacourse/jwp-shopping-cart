package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidCartQuantityException;
import woowacourse.shoppingcart.exception.InvalidProductPriceException;

import java.util.Objects;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int totalPrice;
    private final int quantity;

    public Cart(Long id, Long productId, String name, int price, String imageUrl, int quantity) {
        validatePrice(price);
        validateQuantity(quantity);
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice(quantity, price);
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new InvalidProductPriceException();
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new InvalidCartQuantityException();
        }
    }

    private int calculateTotalPrice(int quantity, int price) {
        return price * quantity;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getPrice() == cart.getPrice() &&
                getQuantity() == cart.getQuantity() &&
                getId().equals(cart.getId()) &&
                getProductId().equals(cart.getProductId()) &&
                getName().equals(cart.getName()) &&
                getImageUrl().equals(cart.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductId(), getName(), getPrice(), getImageUrl(), getQuantity());
    }
}
