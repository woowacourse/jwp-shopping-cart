package cart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final ImageUrl imageUrl;
    private final Price price;

    public Product(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public Product(final Long id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = ImageUrl.imageUrl(imageUrl);
        this.price = Price.price(price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl();
    }

    public int getPrice() {
        return price.getPrice();
    }
}
