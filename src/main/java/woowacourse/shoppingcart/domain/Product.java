package woowacourse.shoppingcart.domain;

public class Product {

    private final Long id;
    private final ProductName name;
    private final Integer price;
    private final ImageUrl imageUrl;

    public Product(final Long id, final ProductName name, final int price, final ImageUrl imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(final ProductName name, final int price, final ImageUrl imageUrl) {
        this(null, name, price, imageUrl);
    }

    public ProductName getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
