package cart.service.dto;

import cart.controller.dto.ProductSaveRequest;

public class ProductSaveDto {

    private final String name;
    private final long price;
    private final String imageUrl;

    private ProductSaveDto(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static ProductSaveDto from(ProductSaveRequest productSaveRequest) {
        return new ProductSaveDto(
                productSaveRequest.getName(),
                productSaveRequest.getPrice(),
                productSaveRequest.getImageUrl()
        );
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
