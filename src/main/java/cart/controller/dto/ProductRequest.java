package cart.controller.dto;

public class ProductRequest {

    private String name;
    private String imageUrl;
    private int price;

    public ProductRequest() {
    }

    public ProductRequest(final String name, final String imageUrl, final int price) {
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
