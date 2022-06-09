package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.PositiveOrZero;

public class ProductRequest {

    private String name;

    @PositiveOrZero(message = "4004")
    private int price;

    private int stockQuantity;

    @JsonProperty("thumbnailImage")
    private ImageDto thumbnailImage;

    public ProductRequest() {
    }

    public ProductRequest(String name, int price, int stockQuantity, ImageDto thumbnailImage) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public ImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
