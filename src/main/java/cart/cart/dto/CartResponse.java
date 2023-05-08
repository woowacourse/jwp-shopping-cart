package cart.cart.dto;

public class CartResponse {

    private final Long id;
    private final String name;
    private final Long price;
    private final String image;

    public CartResponse(Long id, String name, Long price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
