package woowacourse.shoppingcart.ui.dto.response;

public class CartResponse {
    private final long id;
    private final int quantity;
    private final String name;
    private final long price;
    private final String imageUrl;

    public CartResponse(final long id, final int quantity, final String name, final long price, final String imageUrl) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
