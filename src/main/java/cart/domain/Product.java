package cart.domain;
public class Product {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    private Product(Long id, String name, String imageURL, int price) {
        this.id = id;
        this.name = validateName(name);
        this.imageUrl = validateImageUrl(imageURL);
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

    private String validateImageUrl(String imageUrl) {
        return imageUrl;
    }

    private String validateName(String name) {
        return name;
    }

    public Long getId() {
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
