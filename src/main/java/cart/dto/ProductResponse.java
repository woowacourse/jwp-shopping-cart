package cart.dto;

import cart.domain.Product;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String image;

    public ProductResponse(Long id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getImage());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
