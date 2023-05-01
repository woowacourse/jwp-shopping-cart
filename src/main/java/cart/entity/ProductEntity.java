package cart.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class ProductEntity {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
}
