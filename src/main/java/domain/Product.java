package domain;
public class Product {
    private final Long id;
    private final String name;
    private final String imageURL;
    private final int price;

    private Product(Long id, String name, String imageURL, int price) {
        this.id = id;
        this.name = validateName(name);
        this.imageURL = validateImageURL(imageURL);
        this.price = validatePrice(price);
    }

    public static Product createWithId(Long id, String name, String imageURL, int price) {
        return new Product(id, name, imageURL, price);
    }

    public static Product createWithoutId(String name, String imageURL, int price) {
        return new Product(null, name, imageURL, price);
    }


    private int validatePrice(int price) {
        return 0;
    }

    private String validateImageURL(String imageURL) {
        return null;
    }

    private String validateName(String name) {
        return null;
    }

    public Long getId() {
        return id;
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
