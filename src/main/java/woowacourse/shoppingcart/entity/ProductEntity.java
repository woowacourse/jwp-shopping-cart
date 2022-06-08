package woowacourse.shoppingcart.entity;

public class ProductEntity {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final String description;
    private final int stock;

    public ProductEntity(Long id, String name, int price, String imageUrl, String description, int stock) {
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
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                '}';
    }
}
