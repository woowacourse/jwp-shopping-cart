package cart.service.product.dto;

public class InsertProductRequest {
    private final String imageUrl;
    private final String name;
    private final int price;

    public InsertProductRequest(String imageUrl, String name, int price) {
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

    public int getPrice() {
        return price;
    }
}
