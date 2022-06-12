package woowacourse.shoppingcart.dto.response;

public class CartResponse {

    private Long id;
    private Long productId;
    private int quantity;
    private String name;
    private int price;
    private String imageUrl;

    public CartResponse(Long id, Long productId, int quantity, String name, int price, String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
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

    public Long getProductId() {
        return productId;
    }
}
