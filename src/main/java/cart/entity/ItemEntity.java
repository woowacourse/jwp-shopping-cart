package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemEntity {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final int price;

    public ItemEntity(final String name, final String imageUrl, final int price) {
        this.id = null;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
