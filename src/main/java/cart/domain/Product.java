package cart.domain;

public class Product {
    private final String name;
    private final String image;
    private final int price;

    public Product(String name, String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
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
