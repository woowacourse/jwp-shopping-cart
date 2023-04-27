package cart.dto;

import cart.entity.ProductEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDto {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public static ProductDto fromEntity(ProductEntity entity) {
        return new ProductDto(entity.getId(), entity.getName(), entity.getPrice(), entity.getImageUrl());
    }
}
