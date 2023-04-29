package cart.domain.product;

public class Product {

    private final Long id;
    private final Name name;
    private final Image image;
    private final Price price;

    public Product(final String name, final String image, final int price) {
        this(null, name, image, price);
    }

    public Product(final Long id, final Product product) {
        this(id, product.name, product.image, product.price);
    }

    public Product(final Long id, final String name, final String image, final int price) {
        this(id, new Name(name), new Image(image), new Price(price));
    }

    public Product(final Long id, final Name name, final Image image, final Price price) {
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

    public Image getImage() {
        return image;
    }

    public Price getPrice() {
        return price;
    }
}
