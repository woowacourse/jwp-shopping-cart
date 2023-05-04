package cart.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ItemEntity {

    private Long id;
    private String name;
    private String imageUrl;
    private int price;

    private ItemEntity() {
    }

    public ItemEntity(final String name, final String imageUrl, final int price) {
        this.id = null;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
