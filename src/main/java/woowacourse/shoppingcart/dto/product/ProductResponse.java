package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.cart.Product;

public class ProductResponse {

    private Long productId;
    private String thumbnailUrl;
    private String name;
    private int price;
    private int quantity;

    public ProductResponse(Product product) {
        this.productId = product.getId();
        this.thumbnailUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
    }

    private ProductResponse() {
    }

    public Long getProductId() {
        return productId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
