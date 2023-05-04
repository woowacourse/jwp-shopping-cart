package cart.controller.dto.response;

public class ProductResponse {

    private final int id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductResponse(final int id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
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
