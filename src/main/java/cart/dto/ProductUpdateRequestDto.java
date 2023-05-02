package cart.dto;

public class ProductUpdateRequestDto {

    private final Long productId;
    private final String name;
    private final String image;
    private final Long price;

    public ProductUpdateRequestDto(Long productId, String name, String image, long price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public long getPrice() {
        return price;
    }

}
