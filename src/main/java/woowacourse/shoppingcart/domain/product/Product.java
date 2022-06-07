package woowacourse.shoppingcart.domain.product;

public class Product {
    private Long id;
    private Name name;
    private Price price;
    private String imageUrl;

    public Product() {
    }

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = imageUrl;
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public String getName() {
        return name.value();
    }

    public int getPrice() {
        return price.value();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }

    public boolean hasId(Long productId) {
        return id.equals(productId);
    }
}
