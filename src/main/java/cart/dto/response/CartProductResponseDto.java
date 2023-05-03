package cart.dto.response;

import cart.entity.product.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;

public final class CartProductResponseDto {

    @Schema(description = "장바구니 ID")
    private Long id;
    @Schema(description = "상품 ID")
    private Long productId;
    @Schema(description = "상품명")
    private String name;
    @Schema(description = "상품 이미지 URL")
    private String imageUrl;
    @Schema(description = "상품 가격")
    private Integer price;
    @Schema(description = "상품 설명")
    private String description;

    public CartProductResponseDto(
            final Long id,
            final Long productId,
            final String name,
            final String imageUrl,
            final Integer price,
            final String description
    ) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
    }

    public static CartProductResponseDto from(final Long id, final ProductEntity product) {
        return new CartProductResponseDto(
                id,
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                product.getDescription()
        );
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
