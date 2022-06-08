package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final Integer price;
    private final String imageUrl;
    private final Integer quantity;

    public Product(final String name, final int price, final String imageUrl, final int quantity) {
        this(null, name, price, imageUrl, quantity);
    }

    public Product(Long id, String name, Integer price, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
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

    public Integer getQuantity() {
        return quantity;
    }
}
