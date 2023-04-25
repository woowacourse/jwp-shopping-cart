package cart.domain;

public class Product {
    private final Integer id;
    private final String name;
    private final String image;
    private final Long price;

    public Product(final Integer id, Product product) {
        this(id, product.name, product.image, product.price);
    }

    public Product(final String name, final String image, final Long price) {
        this(null, name, image, price);
    }

    private Product(final Integer id, final String name, final String image, final Long price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
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

    public Long getPrice() {
        return price;
    }
}
