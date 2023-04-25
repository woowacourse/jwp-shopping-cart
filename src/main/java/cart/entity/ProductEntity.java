package cart.entity;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    private ProductEntity(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public static ProductEntity of(final Long id, final String name, final String image, final int price) {
        return new ProductEntity(id, name, image, price);
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

    public int getPrice() {
        return price;
    }
}
