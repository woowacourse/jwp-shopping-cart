package woowacourse.shoppingcart.domain;

public class Product {
    private Long id;
    private String name;
    private String imageUrl;
    private Integer price;

    public Product() {
    }

    public Product(Long id, String name, String imageUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public Product(String name, String imageUrl, int price) {
        this(null, name, imageUrl, price);
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
}
