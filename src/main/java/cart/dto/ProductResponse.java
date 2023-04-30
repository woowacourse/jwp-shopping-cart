package cart.dto;

import cart.domain.Product;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    
    @Builder
    public ProductResponse(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }
}
