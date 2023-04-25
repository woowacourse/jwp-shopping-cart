package domain;
public class Product {
    private final String name;
    private final String imageURL;
    private final int price;

    public Product(String name, String imageURL, int price) {
        this.name = name;
        this.imageURL = imageURL;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPrice() {
        return price;
    }
}
