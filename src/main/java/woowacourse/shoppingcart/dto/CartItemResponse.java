package woowacourse.shoppingcart.dto;

public class CartItemResponse {
    private final Long id;
    private final Long productId;
    private final String name;
    private final int price;
    private final int quantity;
    private final String imageUrl;

    public CartItemResponse(Long id, Long productId, String name, int price, int quantity,
      String imageUrl) {
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

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}
