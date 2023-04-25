package cart.domain;

public class Product {

    private final Long id;
    private final String name;
    private final int price;
    private final String image_url;

    public Product(final Long id, final String name, final int price, final String image_url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image_url = image_url;
    }

    public Product(final String name, final int price, final String image_url) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImage_url() {
        return image_url;
    }
}
