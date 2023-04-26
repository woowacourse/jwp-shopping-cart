package cart.domain;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor
public class Product {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
}
