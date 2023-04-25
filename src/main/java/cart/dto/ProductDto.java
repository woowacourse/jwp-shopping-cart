package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductDto {
    private final long id;
    private final String name;
    private final int price;
    private final String imageUrl;
}
