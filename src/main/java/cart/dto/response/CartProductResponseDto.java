package cart.dto.response;

import cart.entity.product.ProductEntity;

public class CartProductResponseDto {

    private Long productId;
    private String name;
    private String imageUrl;
    private Integer price;
    private String description;

    private CartProductResponseDto(
            final Long productId,
            final String name,
            final String imageUrl,
            final Integer price,
            final String description
    ) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
    }

    public static CartProductResponseDto from(final ProductEntity product) {
        return new CartProductResponseDto(
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                product.getDescription()
        );
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
