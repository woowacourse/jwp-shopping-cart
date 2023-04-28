package cart.dto;

import cart.request.ProductRequest;

public class ProductDto {

    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductDto(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductDto from(final ProductRequest productRequest) {
        return new ProductDto(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
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
