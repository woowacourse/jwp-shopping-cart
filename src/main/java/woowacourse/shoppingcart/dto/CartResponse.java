package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int totalPrice;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(final Long id, final Long productId, final String name, final String imageUrl,
                        final int totalPrice, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public static CartResponse from(final Cart cart) {
        final Product product = cart.getProduct();
        return new CartResponse(cart.getId(),
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                cart.calculateTotalPrice(),
                cart.getQuantity());
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
