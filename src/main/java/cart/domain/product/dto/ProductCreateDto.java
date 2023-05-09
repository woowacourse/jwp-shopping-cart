package cart.domain.product.dto;

import cart.dto.request.ProductCreateRequest;

public class ProductCreateDto {

    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductCreateDto(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductCreateDto of(final ProductCreateRequest productCreateRequest) {
        return new ProductCreateDto(productCreateRequest.getName(), productCreateRequest.getPrice(),
            productCreateRequest.getImageUrl());
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
