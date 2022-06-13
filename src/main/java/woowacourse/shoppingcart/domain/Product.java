package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Price price;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = new Price(price);
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
