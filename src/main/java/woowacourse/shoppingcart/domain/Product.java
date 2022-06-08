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

    public Product(Long id, String name, Integer price, String imageUrl, String description, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
        this.stock = stock;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public int getStock() {
        return stock;
    }
}

