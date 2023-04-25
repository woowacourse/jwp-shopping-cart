package cart.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductEntity {
    private final Long id;
    private final String name;
    private final int price;
    private final String imageUrl;
}
