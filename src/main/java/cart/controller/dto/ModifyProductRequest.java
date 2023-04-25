package cart.controller.dto;

public class ModifyProductRequest {

    private final String name;
    private final String image;
    private final int price;

    public ModifyProductRequest(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
