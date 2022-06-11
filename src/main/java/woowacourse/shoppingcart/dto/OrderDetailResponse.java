package woowacourse.shoppingcart.dto;

public class OrderDetailResponse {

    private final long productId;
    private final int quantity;
    private final int price;
    private final String name;
    private final String imageUrl;

    public OrderDetailResponse(long productId, int quantity, int price, String name, String imageUrl) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public long getProductId() {
        return productId;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }
}
