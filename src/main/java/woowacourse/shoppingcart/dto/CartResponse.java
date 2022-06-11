package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private final long id;
    private final long productId;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int totalPrice;
    private final int quantity;

    private CartResponse(long id, long productId, String name, int price, String imageUrl, int totalPrice, int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public CartResponse(Cart cart) {
        this(cart.getId(), cart.getProductId(), cart.getName(), cart.getPrice(),
                cart.getImageUrl(), cart.getTotalPrice(), cart.getQuantity());
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
