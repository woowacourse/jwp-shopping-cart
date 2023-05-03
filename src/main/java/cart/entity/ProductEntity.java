package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductEntity {

    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;

    public ProductEntity(final String name, final int price, final String imageUrl) {
        this(null, name, price, imageUrl);
    }
}
