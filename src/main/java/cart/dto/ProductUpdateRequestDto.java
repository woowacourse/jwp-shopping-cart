package cart.dto;

public class ProductUpdateRequestDto {

    private final String name;
    private final String image;
    private final long price;

    public ProductUpdateRequestDto(final String name, final String image, final long price) {
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

    public long getPrice() {
        return price;
    }
}
