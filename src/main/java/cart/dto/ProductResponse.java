package cart.dto;

import cart.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public static ProductResponse from(final ProductEntity productEntity) {
        return new ProductResponse(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getImageUrl()
        );
    }
}
