package woowacourse.shoppingcart.domain;

public class CartItem {

    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final int quantity;
    private final String imageUrl;

    public CartItem(final Long id, final Long productId, final String name, final int price, final int quantity, final String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
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

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
