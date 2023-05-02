package cart.product.entity;

public class ProductEntity {
    private final Integer id;
    private final String name;
    private final int price;
    private final String image;

    public ProductEntity(final Integer id, final String name, final int price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ProductEntity(final String name, final int price, final String image) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
