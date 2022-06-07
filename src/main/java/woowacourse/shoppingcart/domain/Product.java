package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer stock;
    private final String imageUrl;

    public Product(String name, Integer price, Integer stock, String imageUrl) {
        this(null, name, price, stock, imageUrl);
    }

    public Product(Long id, String name, Integer price, Integer stock, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
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
