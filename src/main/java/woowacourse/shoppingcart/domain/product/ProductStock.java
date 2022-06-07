package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.domain.Quantity;

public class ProductStock {
    private final Product product;
    private final Quantity stockQuantity;

    public ProductStock(Product product, Quantity stockQuantity) {
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

    public Product getProduct() {
        return product;
    }

    public int getStockQuantity() {
        return stockQuantity.getQuantity();
    }
}
