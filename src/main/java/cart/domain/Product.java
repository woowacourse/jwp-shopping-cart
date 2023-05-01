package cart.domain;

public class Product {

    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = new ImageUrl(imageUrl);
    }

    public Product(final String name, final int price, final String imageUrl) {
        this.id = null;
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = new ImageUrl(imageUrl);
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

    public String getImageUrl() {
        return imageUrl.getValue();
    }
}
