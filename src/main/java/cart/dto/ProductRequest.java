package cart.dto;

public class ProductRequest {
    private String name;
    private String image;
    private Long price;

    public ProductRequest() {
    }

    public ProductRequest(final String name, final String image, final Long price) {
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
