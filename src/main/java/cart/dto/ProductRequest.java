package cart.dto;

public class ProductRequest {

    private final String name;
    private final byte[] image;
    private final int price;

    public ProductRequest(final String name, final byte[] image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
