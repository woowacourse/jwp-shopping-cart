package woowacourse.shoppingcart.domain.product;

public class ProductStock {
    private final Product product;
    private final int stockQuantity;

    public ProductStock(Product product, int stockQuantity) {
        this.product = product;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return product.getId();
    }

    public String getName() {
        return product.getName();
    }

    public int getPrice() {
        return product.getPrice();
    }

    public String getThumbnailImageUrl() {
        return product.getThumbnailImageUrl();
    }

    public String getThumbnailImageAlt() {
        return product.getThumbnailImageAlt();
    }

    public int getStockQuantity() {
        return stockQuantity;
    }
}
