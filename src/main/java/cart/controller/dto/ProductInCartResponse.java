package cart.controller.dto;

public class ProductInCartResponse {

    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductInCartResponse(
            final long id,
            final String name,
            final int price,
            final String imageUrl
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }
}
