package woowacourse.shoppingcart.domain;

public class Product {

    private final Id id;
    private final String name;
    private final Integer price;
    private final String imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = new Id(id);
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Long getId() {
        return id.getValue();
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
