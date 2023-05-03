package cart.domain;

public class Product {

    private final Name name;
    private final Price price;
    private final Image imageUrl;

    private Product(final Name name, final Price price, final Image imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public static Product of(final String name, final int price, final String imageUrl) {
        return new Product(
                new Name(name),
                new Price(price),
                new Image(imageUrl)
        );
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl.getUrl();
    }
}
