package woowacourse.shoppingcart.domain.product;

public class Product {

    private Long id;
    private ProductName name;
    private Price price;
    private String image;

    public Product() {
    }

    public Product(Long id, String name, Integer price, String image) {
        this.id = id;
        this.name = ProductName.of(name);
        this.price = Price.of(price);
        this.image = image;
    }

    public Product(final String name, final int price, final String image) {
        this(null, name, price, image);
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public String getImage() {
        return image;
    }

    public Long getId() {
        return id;
    }
}
