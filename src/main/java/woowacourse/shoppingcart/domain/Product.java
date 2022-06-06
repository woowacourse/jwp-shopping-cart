package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final int stock, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final int stock, final String imageUrl) {
        this(null, name, price, stock, imageUrl);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
