package cart.dto;

import java.math.BigDecimal;

public class ProductResponse {

    private final Long productId;
    private final String name;
    private final String image;
    private final BigDecimal price;

    public ProductResponse(Long productId, String name, String image, BigDecimal price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getProductId() {
        return productId;
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
