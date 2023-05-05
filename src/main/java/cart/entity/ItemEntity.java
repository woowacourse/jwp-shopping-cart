package cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemEntity {

    public static ItemEntity empty = new ItemEntity();

    private Long id;
    private String name;
    private String imageUrl;
    private Integer price;

    private ItemEntity() {
    }

    public ItemEntity(final String name, final String imageUrl, final int price) {
        this.id = null;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
