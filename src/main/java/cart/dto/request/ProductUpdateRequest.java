package cart.dto.request;

public class ProductUpdateRequest {
    private Long productId;
    private final String name;
    private final String image;
    private final Long price;

    public ProductUpdateRequest(Long productId, String name, String image, Long price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
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
