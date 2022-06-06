package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private String thumbnail;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Product(final String name, final int price, final String thumbnail) {
        this(null, name, price, thumbnail);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Long getId() {
        return id;
    }
}
