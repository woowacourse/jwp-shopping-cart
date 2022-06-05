package woowacourse.shoppingcart.dto;

public class ProductResponse {
    private final String name;
    private final int price;
    private final String imageUrl;
    private final String description;
    private final int stock;

    public ProductResponse(String name, int price, String imageUrl, String description, int stock) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
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
        return "ProductResponse{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", description='" + description + '\'' +
                ", stock=" + stock +
                '}';
    }
}
