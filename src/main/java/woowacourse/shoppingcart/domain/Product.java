package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private String image;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(final String name, final int price, final String image) {
        this(null, name, price, image);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Long getId() {
        return id;
    }
}
