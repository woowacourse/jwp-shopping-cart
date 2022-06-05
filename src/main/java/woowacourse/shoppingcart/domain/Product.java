package woowacourse.shoppingcart.domain;

public class Product {
    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer stock;
    private final String imageUrl;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
