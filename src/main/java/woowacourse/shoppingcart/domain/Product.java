package woowacourse.shoppingcart.domain;

public class Product {
    private long id;
    private String name;
    private int price;
    private String imageUrl;

    public Product() {
    }

    public Product(final long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(0L, name, price, imageUrl);
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

    public long getId() {
        return id;
    }
}
