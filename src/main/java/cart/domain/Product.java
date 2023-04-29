package cart.domain;

public class Product {

    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(final String name, final String imageUrl, final Integer price) {
        this.name = new Name(name);
        this.imageUrl = new ImageUrl(imageUrl);
        this.price = new Price(price);
    }

    public Name getName() {
        return name;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Price getPrice() {
        return price;
    }
}
