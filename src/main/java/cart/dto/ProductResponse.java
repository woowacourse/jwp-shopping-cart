package cart.dto;

import cart.domain.Product;
import java.math.BigDecimal;

public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final BigDecimal price;

    public ProductResponse(Long id, String name, String image, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
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

    public BigDecimal getPrice() {
        return price;
    }

}
