package cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductModificationRequest {

    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
}