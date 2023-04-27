package cart.repository;

public class ProductDto {
    private final String name;
    private final String image;
    private final Long price;

    public ProductDto(final String name, final String image, final Long price) {
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
