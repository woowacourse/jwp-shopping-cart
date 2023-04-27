package cart.dao;

public class ProductEntity {

    private int id;
    private String name;
    private String image;
    private int price;

    public ProductEntity(final String name, final String image, final int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity(final int id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public ProductEntity update(final ProductEntity productEntity) {
        this.name = productEntity.getName();
        this.image = productEntity.getImage();
        this.price = productEntity.getPrice();
        return this;
    }

    public int getId() {
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

}
