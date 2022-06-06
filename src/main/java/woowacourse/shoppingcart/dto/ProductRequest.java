package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.ThumbnailImage;

public class ProductRequest {
    private final String name;
    private final int price;
    private final int stockQuantity;
    private final ThumbnailImage thumbnailImage;

    public ProductRequest(String name, int price, int stockQuantity, ThumbnailImage thumbnailImage) {
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

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }
}
