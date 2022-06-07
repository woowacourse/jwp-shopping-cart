package woowacourse.shoppingcart.dto.response;

import java.util.Optional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class FindAllProductsResponse {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Long cartId;
    private int quantity;

    public FindAllProductsResponse() {
    }

    public FindAllProductsResponse(final Product product) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), 0L, 0);
    }

    public FindAllProductsResponse(final Product product, final CartItem cartItem) {
        this(product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), cartItem.getId(),
                cartItem.getQuantity());
    }

    public FindAllProductsResponse(final Long id, final String name, final Integer price, final String imageUrl,
                                   final Long cartId, final int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public static FindAllProductsResponse of(final Cart cart, final Product product) {
        final Optional<CartItem> cartItem = cart.findByProductId(product.getId());
        return cartItem.map(item -> new FindAllProductsResponse(product, item))
                .orElseGet(() -> new FindAllProductsResponse(product));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
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
