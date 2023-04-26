package cart.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductRequest {
    private final String name;
    private final String imageUrl;
    private final Integer price;
}
