package cart.dto.entity;

public class ProductEntity {

    private Long id;
    private final String name;
    private final String image;
    private final int price;

    public ProductEntity(String name, String image, int price) {
        this(null, name, image, price);
    }

    public ProductEntity(Long id, String name, String image, int price) {
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

    public int getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
