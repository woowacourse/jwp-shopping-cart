package woowacourse.shoppingcart.domain;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private String description;
    private int stock;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl,
                   final String description, final int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
    }

    public Product(final String name, final int price, final String imageUrl, final String description,
                   final int stock) {
        this(null, name, price, imageUrl, description, stock);
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

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}
