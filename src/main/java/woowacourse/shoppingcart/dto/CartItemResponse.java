package woowacourse.shoppingcart.dto;

public class CartItemResponse {

    private Long id;
    private Long productId;
    private String name;
    private String imageUrl;
    private int price;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(final Long id,
                            final Long productId,
                            final String name,
                            final String imageUrl,
                            final int price,
                            final int quantity) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
