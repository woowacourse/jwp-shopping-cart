package woowacourse.shoppingcart.domain.product;

public class Product {
    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;
    private final boolean selling;
    private final Description description;

    public Product(String name, int price, String imageUrl, boolean selling, String description) {
        this(null, new Name(name), new Price(price), new ImageUrl(imageUrl), selling, new Description(description));
    }

    public Product(Long id, String name, int price, String imageUrl, boolean selling, String description) {
        this(id, new Name(name), new Price(price), new ImageUrl(imageUrl), selling, new Description(description));
    }

    public Product(Long id, Name name, Price price, ImageUrl imageUrl, boolean selling,
            Description description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.selling = selling;
        this.description = description;
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

    public boolean isSelling() {
        return selling;
    }

    public String getDescription() {
        return description.getValue();
    }
}
