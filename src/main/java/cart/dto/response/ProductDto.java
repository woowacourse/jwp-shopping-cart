package cart.dto.response;

import cart.domain.Product;

public class ProductDto {
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public ProductDto(final Integer id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductDto of(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice()
        );
    }

    public Integer getId() {
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
