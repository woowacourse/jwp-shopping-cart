package cart.domain;

public class Product {

    private final ProductId id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImageUrl imageUrl;

    public Product(String name, int price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = new ProductId(id);
        this.name = new ProductName(name);
        this.price = new ProductPrice(price);
        this.imageUrl = new ProductImageUrl(imageUrl);
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

    public String getImageUrl() {
        return imageUrl.getValue();
    }
}
