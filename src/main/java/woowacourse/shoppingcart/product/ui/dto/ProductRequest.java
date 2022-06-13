package woowacourse.shoppingcart.product.ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {
    private String name;

    private Integer price;

    private Long stockQuantity;

    @JsonProperty("thumbnailImage")
    private ThumbnailImageDto thumbnailImage;

    public ProductRequest(String name, Integer price, Long stockQuantity,
                          ThumbnailImageDto thumbnailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
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

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
