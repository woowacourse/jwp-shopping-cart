package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(final CartItem cartItem, final Product product) {
        this(cartItem.getId(), cartItem.getProductId(), product.getName(), product.getPrice(), product.getImageUrl(),
                cartItem.getQuantity());
    }

    public CartItemResponse(final Long id, final Long productId, final String name, final int price,
                            final String imageUrl, final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }
}
