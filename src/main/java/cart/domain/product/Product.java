package cart.domain.product;

public class Product {

    private final Id id;
    private final Name name;
    private final Image image;
    private final Price price;

    public Product(final String productName, final String productImage, final int productPrice) {
        this(null, productName, productImage, productPrice);
    }

    public Product(final Long productId, final Product product) {
        this(new Id(productId), product.name, product.image, product.price);
    }

    public Product(final Long productId, final String productName, final String productImage, final int productPrice) {
        this(new Id(productId), new Name(productName), new Image(productImage), new Price(productPrice));
    }

    public Product(final Id id, final Name name, final Image image, final Price price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Id getProductId() {
        return id;
    }

    public Name getProductName() {
        return name;
    }

    public Image getProductImage() {
        return image;
    }

    public Price getProductPrice() {
        return price;
    }
}
