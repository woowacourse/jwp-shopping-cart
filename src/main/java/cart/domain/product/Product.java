package cart.domain.product;

public class Product {

    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    private Product(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = Name.from(name);
        this.imageUrl = ImageUrl.from(imageUrl);
        this.price = Price.from(price);
    }

    public static Product of(final String name, final String imageUrl, final Integer price) {
        return new Product(null, name, imageUrl, price);
    }

    public static Product of(final Long id, final String name, final String imageUrl, final Integer price) {
        return new Product(id, name, imageUrl, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }

    public Integer getPrice() {
        return price.getPrice();
    }
}
