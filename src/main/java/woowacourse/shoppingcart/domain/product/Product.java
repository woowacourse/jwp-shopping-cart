package woowacourse.shoppingcart.domain.product;

import woowacourse.shoppingcart.domain.product.vo.ImageUrl;
import woowacourse.shoppingcart.domain.product.vo.Name;
import woowacourse.shoppingcart.domain.product.vo.Price;

public class Product {

    private final Long id;
    private final Name name;
    private final Price price;
    private final ImageUrl imageUrl;

    public Product(final Long id, final String name, final int price, final String imageUrl) {
        this.id = id;
        this.name = new Name(name);
        this.price = new Price(price);
        this.imageUrl = new ImageUrl(imageUrl);
    }

    public Product(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public int multiplePrice(int number) {
        return price.multiple(number);
    }

    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public ImageUrl getImageUrl() {
        return imageUrl;
    }

    public Long getId() {
        return id;
    }
}
