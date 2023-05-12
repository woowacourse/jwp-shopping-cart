package cart.service.product.dto;

public class ProductInsertRequest {
    private final String imageUrl;
    private final String name;
    private final Integer price;

    public ProductInsertRequest(String imageUrl, String name, Integer price) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
