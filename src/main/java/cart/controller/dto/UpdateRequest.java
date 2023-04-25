package cart.controller.dto;

public class UpdateRequest {
    private final long id;
    private final String name;
    private final int price;
    private final String image;

    public UpdateRequest(final long id, final String name, final int price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
