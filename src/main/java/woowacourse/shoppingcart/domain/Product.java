package woowacourse.shoppingcart.domain;

public class Product {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final String description;
    private final int stock;

    public Product(String name, int price, String imageUrl, String description, int stock) {
        this(null, name, price, imageUrl, description, stock);
    }

    public Product(Long id, String name, int price, String imageUrl, String description, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
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

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
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
