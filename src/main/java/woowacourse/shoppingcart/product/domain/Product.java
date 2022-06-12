package woowacourse.shoppingcart.product.domain;

import woowacourse.shoppingcart.product.dto.ThumbnailImage;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Long stockQuantity;
    private ThumbnailImage thumbnailImage;

    public Product() {
    }

    public Product(Long id, String name, Integer price, Long stockQuantity, ThumbnailImage thumbnailImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailImage = thumbnailImage;
    }

    public Product(String name, Integer price, Long stockQuantity, ThumbnailImage thumbnailImage) {
        this(null, name, price, stockQuantity, thumbnailImage);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public ThumbnailImage getThumbnailImage() {
        return thumbnailImage;
    }

    public Long getId() {
        return id;
    }
}
