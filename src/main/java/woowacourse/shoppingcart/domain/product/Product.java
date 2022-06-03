package woowacourse.shoppingcart.domain.product;

public class Product {

    private Long id;
    private ProductName name;
    private Price price;
    private ImageUrl imageUrl;

    public Product(String name, Integer price, String imageUrl) {
        this(null, new ProductName(name), new Price(price), new ImageUrl(imageUrl));
    }

    public Product(Long id, String name, Integer price, String imageUrl) {
        this(id, new ProductName(name), new Price(price), new ImageUrl(imageUrl));
    }

    public Product(Long id, ProductName name, Price price, ImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public Integer getPrice() {
        return price.getAmount();
    }

    public String getImageUrl() {
        return imageUrl.getValue();
    }
}
