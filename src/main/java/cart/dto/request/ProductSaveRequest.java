package cart.dto.request;

public class ProductSaveRequest {
    private final String name;
    private final String image;
    private final Long price;

    public ProductSaveRequest(String name, String image, Long price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }
}
