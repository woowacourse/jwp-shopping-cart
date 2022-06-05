package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final int stockQuantity;
    private final Image image;

    public ProductResponse(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.image = product.getImage();
    }

    public Long getId() {
        return id;
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

    public Image getImage() {
        return image;
    }
}
