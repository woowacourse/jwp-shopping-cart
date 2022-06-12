package woowacourse.shoppingcart.domain.product;

public class Product {

    private final Long id;
    private final ProductName name;
    private final Price price;
    private final ImageUrl imageUrl;
    private final String description;

    public Product(String name, Integer price, String imageUrl, String description) {
        this(null, new ProductName(name), new Price(price), new ImageUrl(imageUrl), description);
    }

    public Product(Long id, String name, Integer price, String imageUrl, String description) {
        this(id, new ProductName(name), new Price(price), new ImageUrl(imageUrl), description);
    }

    public Product(Long id, ProductName name, Price price, ImageUrl imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
