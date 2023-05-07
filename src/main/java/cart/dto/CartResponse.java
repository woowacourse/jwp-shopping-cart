package cart.dto;

import cart.entity.CartItem;
import cart.entity.Product;

public class CartResponse {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final int price;

    public CartResponse(CartItem cartItem, Product product) {
        this.id = cartItem.getId();
        this.name = product.getName();
        this.imgUrl = product.getImgUrl();
        this.price = product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getPrice() {
        return price;
    }
}
