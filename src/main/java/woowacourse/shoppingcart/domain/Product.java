package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.exception.InvalidProductPriceException;

import java.util.Objects;

public class Product {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public Product(long id, String name, int price, String imageUrl) {
        validatePrice(price);
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, int price, String imageUrl) {
        this(0L, name, price, imageUrl);
    }

    private void validatePrice(int price) {
        if (price <= 0) {
            throw new InvalidProductPriceException();
        }
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

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(getImageUrl(), product.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(), getImageUrl());
    }
}
