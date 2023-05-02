package cart.persistance.entity;

public class CartProductEntity {

    private final long id;
    private final String name;
    private final long price;
    private final String imageUrl;

    public CartProductEntity(final long id, final String name, final long price, final String imageUrl) {
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

    public long getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
