package cart.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CartItemEntity {
    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;
}
