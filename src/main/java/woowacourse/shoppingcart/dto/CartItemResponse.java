package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int price;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(final Long id,
                            final Long productId,
                            final String name,
                            final String imageUrl,
                            final int price,
                            final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponse of(final CartItem cartItem, final Product product) {
        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                cartItem.getQuantity()
        );
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

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
