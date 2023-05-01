package cart.dto.response;

import cart.entity.product.ProductEntity;

public class CartProductResponseDto {

    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private Integer price;
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
