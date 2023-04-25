package cart.controller.dto;

public class ProductRequest {

    private final String name;
    private final Integer price;
    private final String image;

    public ProductRequest(final String name, final Integer price, final String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

}
