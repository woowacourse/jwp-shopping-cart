package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Cart {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final String imageUrl;

    private Cart(Long id, Long productId, String name, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Cart(Long id, Product product) {
        this(id, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return getPrice() == cart.getPrice() &&
                getId().equals(cart.getId()) &&
                getProductId().equals(cart.getProductId()) &&
                getName().equals(cart.getName()) &&
                getImageUrl().equals(cart.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductId(), getName(), getPrice(), getImageUrl());
    }
}
