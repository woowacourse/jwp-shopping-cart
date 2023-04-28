package cart.entity;

public class Product {


    private final Long id;
    private String name;
    private Integer price;
    private String imageUrl;

    public Product(final Long id, final String name, final Integer price, final String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final Integer price) {
        this.price = price;
    }

    public void setImageUrl(final String imageUrl) {
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

    public String getImageUrl() {
        return imageUrl;
    }
}
