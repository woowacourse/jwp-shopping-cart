package cart.web.controller.cart.dto.response;

public class CartProductResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final String category;
    private final String imageUrl;

    public CartProductResponse(Long id, String name, int price, String category, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
