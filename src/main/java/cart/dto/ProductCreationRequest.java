package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductCreationRequest {

    private final String name;
    private final String imageUrl;
    private final Integer price;
}
