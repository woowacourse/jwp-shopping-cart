package cart.dto;

import cart.domain.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }
}
