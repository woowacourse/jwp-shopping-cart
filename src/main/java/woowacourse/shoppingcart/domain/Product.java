package woowacourse.shoppingcart.domain;

public class Product {

    private Long id;
    private String name;
    private int price;
    private String imageUrl;
    private int stock;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl, final int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Product(final String name, final int price, final String imageUrl, final int stock) {
        this(null, name, price, imageUrl, stock);
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

    public Long getId() {
        return id;
    }

    public int getStock() {
        return stock;
    }
}
