package cart.dto;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    private ProductDto(final Long id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto of(final String name, final String image, final Integer price) {
        return new ProductDto(null, name, image, price);
    }

    public static ProductDto of(final Long id, final String name, final String image, final Integer price) {
        return new ProductDto(id, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
