package cart.domain.cart.dto;

public class ProductResponse {
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductResponse(String name, String imageUrl, int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
