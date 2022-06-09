package woowacourse.shoppingcart.domain.product;

public class Product {

    private final ProductId productId;
    private final Name name;
    private final Price price;
    private final Thumbnail thumbnail;

    public Product(final ProductId productId, final Name name, final Price price, final Thumbnail thumbnail) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }

    public ProductId getId() {
        return productId;
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public boolean isSame(final Product product) {
        return product.productId.equals(this.productId);
    }
}
