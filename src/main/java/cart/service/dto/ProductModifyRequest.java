package cart.service.dto;

public class ProductModifyRequest {

    private String name;
    private int price;
    private String imageUrl;

    public ProductModifyRequest() {
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
