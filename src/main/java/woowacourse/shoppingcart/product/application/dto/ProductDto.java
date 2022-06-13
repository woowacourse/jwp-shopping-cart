package woowacourse.shoppingcart.product.application.dto;

import woowacourse.shoppingcart.product.ui.dto.ProductRequest;
import woowacourse.shoppingcart.product.ui.dto.ThumbnailImageDto;

public class ProductDto {

    private final String name;

    private final Integer price;

    private final Long stockQuantity;

    private final ThumbnailImageDto thumbnailImageDto;

    private ProductDto(String name, Integer price, Long stockQuantity,
                       ThumbnailImageDto thumbnailImageDto) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImageDto = thumbnailImageDto;
    }

    public static ProductDto from(ProductRequest productRequest) {
        return new ProductDto(productRequest.getName(), productRequest.getPrice(), productRequest.getStockQuantity(),
                productRequest.getThumbnailImage());
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public ThumbnailImageDto getThumbnailImageDto() {
        return thumbnailImageDto;
    }
}
