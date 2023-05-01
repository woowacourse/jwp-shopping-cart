package cart.dto;

public class ProductResponse {
    private final Long id;
    private final String name;
    private final String image;
    private final long price;

    public ProductResponse(Long id, String name, String image, long price) {
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

    public long getPrice() {
        return price;
    }
}
