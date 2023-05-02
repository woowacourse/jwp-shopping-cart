package cart.dto;

public class ProductDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    private ProductDto(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public static ProductDto of(final String name, final String image_url, final Integer price) {
        return new ProductDto(null, name, image_url, price);
    }

    public static ProductDto of(final Long id, final String name, final String image_url, final Integer price) {
        return new ProductDto(id, name, image_url, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
