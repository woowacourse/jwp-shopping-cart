package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.product.ProductStock;

public class ProductResponse {
    private long id;
    private String name;
    private int price;
    private Integer stockQuantity;
    private ThumbnailImageDto thumbnailImage;

    public ProductResponse() {
    }

    private ProductResponse(long id, String name, int price, Integer stockQuantity,
        ThumbnailImageDto thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static ProductResponse from(ProductStock product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStockQuantity(),
            new ThumbnailImageDto(
                product.getThumbnailImageUrl(), product.getThumbnailImageAlt()));
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public ThumbnailImageDto getThumbnailImage() {
        return thumbnailImage;
    }
}
