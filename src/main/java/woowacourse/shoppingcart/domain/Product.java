package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private String thumbnail;
    private Integer price;

    public Product() {
    }

    public Product(Long id, String name, int price, String thumbnail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public Product(String name, int price, String thumbnail) {
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
