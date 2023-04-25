package cart.entity;

public class Product {

    private final Long id;
    private final String name;
    private final byte[] image;
    private final int price;

    public Product(final Long id, final String name, final byte[] image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(final String name, final byte[] image, final int price) {
        this(null, name, image, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
