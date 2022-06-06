package woowacourse.shoppingcart.domain.product;

public class Product {
    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;
    private final boolean deleted;

    public Product(String name, int price, String imageUrl, boolean deleted) {
        this(null, new Name(name), new Price(price), new ImageUrl(imageUrl), deleted);
    }

    public Product(Long id, String name, int price, String imageUrl, boolean deleted) {
        this(id, new Name(name), new Price(price), new ImageUrl(imageUrl), deleted);
    }

    public Product(Long id, Name name, Price price, ImageUrl imageUrl, boolean deleted) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }
}
