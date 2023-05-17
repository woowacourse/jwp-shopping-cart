package cart.controller.dto;

public class ProductResponse {
    private final Long id;
    private final String imageUrl;
    private final String name;
    private final int price;

    public ProductResponse(Long id, String imageUrl, String name, int price) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
