package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class FindCartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private int price;
    private String imageUrl;
    private int quantity;
    private boolean checked;

    public FindCartItemResponse() {
    }

    public FindCartItemResponse(CartItem cartItem, Product product) {
        this.id = cartItem.getCartItemId();
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
        this.quantity = cartItem.getQuantity();
        this.checked = cartItem.getChecked();
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

    public int getQuantity() {
        return quantity;
    }

    public boolean getChecked() {
        return checked;
    }

    public Long getProductId() {
        return productId;
    }
}
