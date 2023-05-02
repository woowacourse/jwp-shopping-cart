package cart.dto.response;

import cart.entity.product.ProductEntity;
import java.util.List;
import java.util.stream.Collectors;

public class CartProductResponseDto {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    private final String description;

    private CartProductResponseDto(final Long id, final String name, final String imageUrl, final Integer price,
        final String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
    }

    public static CartProductResponseDto of(final ProductEntity productEntity) {
        return new CartProductResponseDto(
            productEntity.getId(),
            productEntity.getName(),
            productEntity.getImageUrl(),
            productEntity.getPrice(),
            productEntity.getDescription()
        );
    }

    public static List<CartProductResponseDto> asList(final List<ProductEntity> productEntities) {
        return productEntities.stream()
            .map(CartProductResponseDto::of)
            .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
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
