package cart.entity;

public class Product {
    private final Long id;
    private final String name;
    private final String imgUrl;
    private final Integer price;

    public Product(Long id, String name, String imgUrl, Integer price) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Integer getPrice() {
        return price;
    }
}
