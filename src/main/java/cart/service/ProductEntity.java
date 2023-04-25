package cart.service;

public class ProductEntity {

    private int id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductEntity(final String name, final int price, final String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
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
}
