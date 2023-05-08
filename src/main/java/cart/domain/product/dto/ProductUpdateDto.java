package cart.domain.product.dto;

import cart.dto.ProductUpdateRequest;

public class ProductUpdateDto {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductUpdateDto(final Long id, final String name, final int price,
        final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductUpdateDto of(final Long id,
        final ProductUpdateRequest productUpdateRequest) {
        return new ProductUpdateDto(id, productUpdateRequest.getName(),
            productUpdateRequest.getPrice(), productUpdateRequest.getImageUrl());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
