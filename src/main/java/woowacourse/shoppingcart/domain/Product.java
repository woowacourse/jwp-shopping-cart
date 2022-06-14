package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
    private final Amount quantity;

    public Product(Long id, String name, int price, String imageUrl, Integer quantity) {
        this(id, name, price, imageUrl, new Amount(quantity));
    }

    public Product(Long id, String name, int price, String imageUrl, Amount quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public boolean isAvailable(Amount count) {
        return quantity.isMoreThan(count);
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
        return quantity.getValue();
    }
}
