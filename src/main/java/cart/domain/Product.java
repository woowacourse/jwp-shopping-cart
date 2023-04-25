package cart.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

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
