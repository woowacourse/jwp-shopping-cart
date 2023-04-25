package cart.entity;

public class Product {
    private final Integer id;
    private final String name;
    private final String image;
    private final Integer price;

    public Product(final Integer id, final String name, final String image, final Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(final String name, final String image, final Integer price) {
        this(null, name, image, price);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
