package woowacourse.shoppingcart.domain;

public class Product {

    private Long id;
    private String name;
    private Integer price;
    private String imageUrl;
    private Integer quantity;

    public Product() {
    }

    public Product(Long id, String name, int price, String imageUrl, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public Product(String name, int price, String imageUrl, int quantity) {
        this(null, name, price, imageUrl, quantity);
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

    public int getQuantity() {
        return quantity;
    }
}
