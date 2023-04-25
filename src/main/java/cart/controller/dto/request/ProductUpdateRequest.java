package cart.controller.dto.request;

public class ProductUpdateRequest {
    private String name;
    private int price;
    private String imageUrl;

    public ProductUpdateRequest() {

    }

    public ProductUpdateRequest(String name, int price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

}
