package woowacourse.shoppingcart.dto.cart;

public class CartResponse {

    private final int productId;
    private final String name;
    private final int price;
    private final String thumbnail;
    private final int quantity;

    public CartResponse(final int productId, final String name, final int price, final String thumbnail, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public int getQuantity() {
        return quantity;
    }
}
