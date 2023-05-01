package cart.dao.entity;

public class ProductEntity {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String image;

    public ProductEntity(final Long id, final String name, final Integer price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public ProductEntity(final String name, final Integer price, final String image) {
        this(null, name, price, image);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }
}
