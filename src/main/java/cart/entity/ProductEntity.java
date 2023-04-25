package cart.entity;

public class ProductEntity {

    private Long id;
    private String name;
    private String image;
    private Integer price;

    public ProductEntity(Long id, String name, String image, Integer price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrice() {
        return price;
    }
}
