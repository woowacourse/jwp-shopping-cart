package cart.controller.dto.response;

public class CartItemResponse {
    private Long id;
    private String name;
    private String image;
    private Long price;

    public CartItemResponse(final Long id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }
}
