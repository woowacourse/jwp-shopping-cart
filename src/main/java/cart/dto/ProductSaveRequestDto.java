package cart.dto;

public class ProductSaveRequestDto {

    private final String name;
    private final String image;
    private final Long price;

    public ProductSaveRequestDto(String name, String image, long price) {
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
