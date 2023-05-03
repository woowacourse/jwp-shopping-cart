package cart.persistance.entity.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final ProductPrice price;
    private final ProductImageUrl imageUrl;

    private Product(
            final Long id,
            final ProductName name,
            final ProductPrice price,
            final ProductImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product create(
            final Long id,
            final String name,
            final long price,
            final String imageUrl
    ) {
        return new Product(
                id,
                new ProductName(name),
                new ProductPrice(price),
                new ProductImageUrl(imageUrl));
    }

    public static Product createWithoutId(
            final String name,
            final long price,
            final String imageUrl
    ) {
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
