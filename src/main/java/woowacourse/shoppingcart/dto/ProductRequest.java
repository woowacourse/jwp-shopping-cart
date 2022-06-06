package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductRequest {
    @NotBlank
    private String name;
    @NotNull
    private Integer price;
    @NotNull
    private Integer stockQuantity;

    private ThumbnailImageDto thumbnailImage;

    public ProductRequest() {
    }

    public ProductRequest(String name, Integer price, Integer stockQuantity,
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
