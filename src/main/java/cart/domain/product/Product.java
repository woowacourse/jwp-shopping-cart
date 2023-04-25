package cart.domain.product;

public class Product {

    private final Long id;
    private final String name;
    private final long price;
    private final String imageUrl;

    public Product(Long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, long price, String imageUrl) {
        this(null, name, price, imageUrl);
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
