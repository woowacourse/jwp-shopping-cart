package woowacourse.shoppingcart.ui.dto.response;

public class CartResponse {

    private Long id;
    private int quantity;
    private String name;
    private int price;
    private String imageUrl;

    public CartResponse(Long id, int quantity, String name, int price, String imageUrl) {
        this.id = id;
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
}
