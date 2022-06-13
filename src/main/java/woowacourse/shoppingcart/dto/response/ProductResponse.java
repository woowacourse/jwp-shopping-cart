package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class ProductResponse {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private Long cartId;
    private int quantity;

    private ProductResponse() {
    }

    private ProductResponse(final Long id, final String name, final int price,
                            final String imageUrl, final Long cartId, final int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public static ProductResponse withCart(final Cart cart) {
        final Product product = cart.getProduct();
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                cart.getId(),
                cart.getQuantity());
    }

    public static ProductResponse withOutCart(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                null,
                0);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getCartId() {
        return cartId;
    }

    public int getQuantity() {
        return quantity;
    }
}
