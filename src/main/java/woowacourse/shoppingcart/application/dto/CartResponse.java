package woowacourse.shoppingcart.application.dto;

public class CartResponse {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    public CartResponse() {
    }

    public CartResponse(final Long id, final String name, final int price, final int quantity, final String imageUrl) {
        this.id = id;
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
