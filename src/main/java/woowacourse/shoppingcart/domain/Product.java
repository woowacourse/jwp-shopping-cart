package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final int stock;

    public Product(Long id, String name, int price, String imageUrl, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Product(String name, int price, String imageUrl, int stock) {
        this(null, name, price, imageUrl, stock);
    }

    public Long getId() {
        return id;
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

    public int getStock() {
        return stock;
    }
}
