package cart.dto;

public class ProductUpdateDto {
    private final Integer id;
    private final String name;
    private final Integer price;
    private final String image;

    public ProductUpdateDto(final Integer id, final String name, final String image, final Integer price) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
