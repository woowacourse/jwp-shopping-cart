package cart.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Product {
    private final String name;
    private final int price;
    private final String imageUrl;
}
