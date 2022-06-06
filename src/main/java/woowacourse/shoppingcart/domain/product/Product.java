package woowacourse.shoppingcart.domain.product;

public class Product {
    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public Product(String name, int price, String imageUrl) {
        this(null, new Name(name), new Price(price), new ImageUrl(imageUrl));
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this(id, new Name(name), new Price(price), new ImageUrl(imageUrl));
    }

    public Product(Long id, Name name, Price price, ImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
