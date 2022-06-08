package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {

    private Long productId;
    private String thumbnail;
    private String name;
    private Integer price;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long productId, String thumbnail, String name, Integer price, Integer quantity) {
        this.productId = productId;
        this.thumbnail = thumbnail;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponse from(Product product, Integer quantity) {
        return new CartItemResponse(
                product.getId(), product.getImageUrl(), product.getName(), product.getPrice(), quantity);
    }

    public Long getProductId() {
        return productId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
