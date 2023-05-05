package cart.domain.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImage image;


    public Product(final String name, final int price, final String image) {
        this(null, name, price, image);
    }

    public Product(final Long id, final String name, final int price, final String image) {
        this.id = id;
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.image = new ProductImage(image);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImage() {
        return image.getValue();
    }
}
