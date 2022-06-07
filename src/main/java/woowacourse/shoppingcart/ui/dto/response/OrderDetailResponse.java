package woowacourse.shoppingcart.ui.dto.response;

public class OrderDetailResponse {
    private final long productId;
    private final long quantity;
    private final long price;
    private final String name;
    private final String imageUrl;

    public OrderDetailResponse(final long productId, final long quantity, final long price, final String name,
                               final String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
