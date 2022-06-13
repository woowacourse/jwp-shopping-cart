package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private int stock;
    private final String imageUrl;

    public Product(String name, int price, int stock, String imageUrl) {
        this(null, name, price, stock, imageUrl);
    }

    public Product(Long id, String name, int price, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public void receive(int quantity) {
        validateQuantity(quantity);
        this.stock += quantity;
    }

    public void release(int quantity) {
        validateQuantity(quantity);
        validateStock(quantity);
        this.stock -= quantity;
    }

    private void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("상품 수량은 0보다 커야 합니다.");
        }
    }

    public void validateStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고가 충분하지 않습니다.");
        }
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

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(price, product.price) && Objects.equals(stock, product.stock)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock, imageUrl);
    }
}
