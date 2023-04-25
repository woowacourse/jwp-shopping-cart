package cart.domain;

public class Product {

    private final Integer id;
    private final String name;
    private final int price;
    private final String image;

    public Product(Integer id, String name, int price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String name, int price, String image) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public Product(String name, int price) {
        this.id = null;
        this.name = name;
        this.price = price;
        this.image = null;
    }
}
