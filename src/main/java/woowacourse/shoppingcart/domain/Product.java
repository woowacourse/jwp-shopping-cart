package woowacourse.shoppingcart.domain;

public class Product {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Image image;

    public Product(String name, int price, int stockQuantity, Image image) {
        this(null, name, price, stockQuantity, image);
    }

    public Product(Long id, String name, int price, int stockQuantity, Image image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.image = image;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Image getImage() {
        return image;
    }
}
