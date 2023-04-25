package cart.dto;

import cart.domain.Product;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class ProductDto {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    
    public static ProductDto from(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }
}
