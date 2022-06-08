package woowacourse.shoppingcart.domain.product;

import java.util.List;

public class Product {

    private final ProductId productId;
    private final Name name;
    private final Price price;
    private final Thumbnail thumbnail;

    public Product(ProductId productId, Name name, Price price, Thumbnail thumbnail) {
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

    public boolean isSame(Product product) {
        return product.productId.equals(this.productId);
    }
}
