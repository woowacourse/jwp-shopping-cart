package woowacourse.shoppingcart.dto.cart;

import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.product.Product;

public class CartSetResponse {
    private Long id;
    private Long productId;
    private String image;
    private String name;
    private int price;
    private int quantity;
    private boolean isCreated;

    public CartSetResponse(Product product, Cart cart, boolean isCreated) {
        this.id = cart.getId();
        this.productId = product.getId();
        this.image = product.getImage();
        this.name = product.getName();
        this.price = product.getPrice();
        this.quantity = cart.getQuantity();
        this.isCreated = isCreated;
    }

    public Long getProductId() {
        return productId;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isCreated() {
        return isCreated;
    }

    public Long getId() {
        return id;
    }
}
