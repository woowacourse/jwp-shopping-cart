package woowacourse.shoppingcart.dto;

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

    public ProductResponse(final Product product) {
        this(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl(), null, 0);
    }

    public ProductResponse(final Long id, final String name, final int price, final String imageUrl,
                           final Long cartId, final int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.cartId = cartId;
        this.quantity = quantity;
    }

    public void addCartQuantity(final Cart cart) {
        this.cartId = cart.getId();
        this.quantity = cart.getQuantity().getValue();
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
