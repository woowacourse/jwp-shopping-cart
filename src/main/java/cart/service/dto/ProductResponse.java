package cart.service.dto;

public class ProductResponse {

    private final long id;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final String category;

    public ProductResponse(final long id, final String productName,
                           final String imageUrl,
                           final int price, final String category) {
        this.id = id;
        this.name = productName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.category = category;
    }

    public long getId() {
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

    public String getCategory() {
        return category;
    }
}
