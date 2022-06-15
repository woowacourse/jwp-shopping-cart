package woowacourse.shoppingcart.product.domain;

public class Product {

    private static final long TEMPORARY_ID = 0;

    private long id;
    private String name;
    private Integer price;
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
        this(TEMPORARY_ID, name, price, imageUrl);
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
