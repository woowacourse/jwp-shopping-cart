package cart.dto.response;

import cart.domain.Product;

public class ProductDto {
    private final int id;
    private final String name;
    private final String image;
    private final int price;

    public ProductDto(final int id, final String name, final String image, final int price) {
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
