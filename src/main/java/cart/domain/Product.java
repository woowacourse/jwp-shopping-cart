package cart.domain;

public class Product {

    private final Long id;
    private final Name name;
    private final ImageUrl image;
    private final Price price;

    public Product(final Name name, final ImageUrl image, final Price price) {
        this(null, name, image, price);
    }

    public Product(final Long id, final Name name, final ImageUrl image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public ImageUrl getImage() {
        return image;
    }

    public Price getPrice() {
        return price;
    }
}
