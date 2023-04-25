package cart.domain;

public class Product {
    private static final Long DEFAULT = null;
    private Long productId;
    private final String name;
    private final String image;
    private final Long price;

    public Product(Long productId, String name, String image, long price) {
        this.productId = productId;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static Product of(String name, String image, long price) {
        return new Product(DEFAULT, name, image, price);
    }

    public long getProductId() {
        return productId;
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
