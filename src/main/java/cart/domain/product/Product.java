package cart.domain.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImageUrl imageUrl;

    private Product(Long id, ProductName name, ProductPrice price, ProductImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product create(Long id, String name, long price, String imageUrl) {
        return new Product(
                id,
                new ProductName(name),
                new ProductPrice(price),
                new ProductImageUrl(imageUrl));
    }

    public static Product createWithoutId(String name, long price, String imageUrl) {
        return create(null, name, price, imageUrl);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public long getPrice() {
        return price.getAmount();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }
}
