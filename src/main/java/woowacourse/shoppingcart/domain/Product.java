package woowacourse.shoppingcart.domain;

import java.util.Objects;

public class Product {

    private final long id;
    private final String name;
    private final int price;
    private final int stock;
    private final String imageUrl;

    public Product(long id, String name, int price, int stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public long getId() {
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

    public boolean hasLowerStock(int purchasingQuantity) {
        return stock < purchasingQuantity;
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
        return id == product.id && price == product.price && stock == product.stock && Objects
                .equals(name, product.name) && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, stock, imageUrl);
    }
}
