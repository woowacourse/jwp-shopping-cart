package cart.domain.product;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImage image;


    public Product(String name, int price, String image) {
        this(null, name, price, image);
    }

    public Product(Long id, String name, int price, String image) {
        this.id = new ProductId(id);
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.image = new ProductImage(image);
    }

    public Long getId() {
        return id.getValue();
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
