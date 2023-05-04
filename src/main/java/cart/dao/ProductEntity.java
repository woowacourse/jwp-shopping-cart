package cart.dao;

public class ProductEntity {

    private final Integer id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ProductEntity(final String name, final String imageUrl, final int price) {
        this(null, name, imageUrl, price);
    }

    public ProductEntity(final Integer id, final String name, final String imageUrl, final int price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

}
