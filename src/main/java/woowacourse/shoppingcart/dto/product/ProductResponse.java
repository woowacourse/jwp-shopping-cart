package woowacourse.shoppingcart.dto.product;

import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long productId;
    private String name;
    private Integer price;
    private String thumbnailUrl;
    private Integer quantity;

    private ProductResponse() {
    }

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getQuantity());
    }

    public ProductResponse(Long productId, String name, Integer price, String thumbnailUrl, Integer quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnailUrl = thumbnailUrl;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
