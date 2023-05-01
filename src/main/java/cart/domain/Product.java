package cart.domain;

public class Product {

    private final Id id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public Product(final long id, final String name, final int price, final String imageUrl) {
        this.id = new Id(id);
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
