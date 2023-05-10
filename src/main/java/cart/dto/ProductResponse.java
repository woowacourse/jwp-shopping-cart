package cart.dto;

import cart.entity.ProductEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductResponse {

    @ApiModelProperty(value = "상품 ID", example = "상품 ID")
    private final Long id;

    @ApiModelProperty(value = "상품 이름", example = "상품 이름")
    private final String name;

    @ApiModelProperty(value = "상품 가격", example = "상품 가격")
    private final int price;

    @ApiModelProperty(value = "상품 이미지 URL", example = "상품 이미지 URL")
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
