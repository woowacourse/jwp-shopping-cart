package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private Long quantity;
    private boolean checked;

    private CartItemResponse() {
    }

    private CartItemResponse(Long id, Long productId, String name, int price, String imageUrl, Long quantity, boolean checked) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.checked = checked;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(cartItem.getId(), cartItem.getProduct().getId(),
                cartItem.getProduct().getName(), cartItem.getProduct().getPrice(), cartItem.getProduct().getImageUrl(),
                cartItem.getQuantity(), cartItem.isChecked());
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
