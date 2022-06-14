package woowacourse.shoppingcart.ui.dto.response;

public class CartResponse {
    private long id;
    private int quantity;
    private long productId;
    private String name;
    private long price;
    private String imageUrl;

    public CartResponse() {
    }

    public CartResponse(final long id, final int quantity, final long productId, final String name, final long price,
                        final String imageUrl) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
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

    public long getProductId() {
        return productId;
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
