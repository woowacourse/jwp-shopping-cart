package woowacourse.shoppingcart.domain.product;

public class Product {
    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;
    private final boolean selling;

    public Product(String name, int price, String imageUrl, boolean selling) {
        this(null, new Name(name), new Price(price), new ImageUrl(imageUrl), selling);
    }

    public Product(Long id, String name, int price, String imageUrl, boolean selling) {
        this(id, new Name(name), new Price(price), new ImageUrl(imageUrl), selling);
    }

    public Product(Long id, Name name, Price price, ImageUrl imageUrl, boolean selling) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.selling = selling;
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
}
