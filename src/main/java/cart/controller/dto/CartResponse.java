package cart.controller.dto;

public class CartResponse {

    private final String name;
    private final String imageUrl;
    private final int price;

    public CartResponse(final String name, final String imageUrl, final int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
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
}
