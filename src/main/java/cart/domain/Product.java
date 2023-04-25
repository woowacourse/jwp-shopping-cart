package cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Product {

    private Long id;
    private final String name;
    private final String image;
    private final int price;

    public Product(final Long id, final String name, final String image, final int price) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Product(final String name, final String image, final int price) {
        this(null, name, image, price);
    }
}
