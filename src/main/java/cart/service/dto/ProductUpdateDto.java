package cart.service.dto;

import cart.controller.dto.ProductUpdateRequest;

public class ProductUpdateDto {

    private final long id;
    private final String name;
    private final long price;
    private final String imageUrl;

    private ProductUpdateDto(long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductUpdateDto createWithIdAndRequest(long id, ProductUpdateRequest productUpdateRequest) {
        return new ProductUpdateDto(
                id,
                productUpdateRequest.getName(),
                productUpdateRequest.getPrice(),
                productUpdateRequest.getImageUrl()
        );
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
