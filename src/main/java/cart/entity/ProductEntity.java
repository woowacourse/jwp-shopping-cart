package cart.entity;

public class ProductEntity {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductEntity(Long id, String name, String imageUrl, int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

}

