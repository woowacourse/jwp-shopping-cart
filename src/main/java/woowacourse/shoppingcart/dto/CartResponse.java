package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private Long productId;
    private String name;
    private Integer price;
    private String thumbnail;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(Long productId, String name, Integer price, String thumbnail, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
    }

    public CartResponse(Cart cart) {
        this(cart.getProductId(), cart.getName(), cart.getPrice(), cart.getImageUrl(), cart.getQuantity());
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

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }
}
