package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartResponse {

    private Long id;
    private int quantity;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;

    private CartResponse() {
    }

    public CartResponse(final Cart cart) {
        final Product product = cart.getProduct();
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }
    
    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
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
}
