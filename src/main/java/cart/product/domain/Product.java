package cart.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Product {
    private final Long id;
    private final Name name;
    private final ImageUrl imageUrl;
    private final Price price;
    
    public Product(final String name, final String imageUrl, final Integer price) {
        this(null, name, imageUrl, price);
    }
    
    public Product(final Long id, final String name, final String imageUrl, final Integer price) {
        this(id, new Name(name), new ImageUrl(imageUrl), new Price(price));
    }
    
    private Product(final Long id, final Name name, final ImageUrl imageUrl, final Price price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
