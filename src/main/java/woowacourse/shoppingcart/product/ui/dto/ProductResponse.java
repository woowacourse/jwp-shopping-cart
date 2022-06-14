package woowacourse.shoppingcart.product.ui.dto;

import woowacourse.shoppingcart.product.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private Long stockQuantity;
    private ThumbnailImageDto thumbnailImage;

    public ProductResponse(Long id, String name, Integer price, Long stockQuantity,
                           ThumbnailImageDto thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                product.getStockQuantity(), ThumbnailImageDto.from(product.getThumbnailImage()));
    }

    public Long getId() {
        return id;
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
