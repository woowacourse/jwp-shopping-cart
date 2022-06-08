package woowacourse.shoppingcart.domain.product;

import java.util.Objects;

public class Product {
    private final Long id;
    private final String name;
    private final Price price;
    private final String imageUrl;
    private final String description;
    private final Stock stock;

    private Product(String name, Price price, String imageUrl, String description, Stock stock) {
        this(null, name, price, imageUrl, description, stock);
    }

    private Product(Long id, String name, Price price, String imageUrl, String description, Stock stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
    }

    public static Product of(Long id, String name, int price, String imageUrl, String description, int stock) {
        return new Product(id, name, new Price(price), imageUrl, description, new Stock(stock));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public Stock getStock() {
        return stock;
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
                && Objects.equals(price, product.price) && Objects.equals(imageUrl, product.imageUrl)
                && Objects.equals(description, product.description) && Objects.equals(stock,
                product.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imageUrl, description, stock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                '}';
    }
}
