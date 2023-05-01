package cart.dto;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    private ProductResponse(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = imageUrl;
        this.price = price;
    }

    public static ProductResponse of(final Long id, final String name, final String image_url, final Integer price) {
        return new ProductResponse(id, name, image_url, price);
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
