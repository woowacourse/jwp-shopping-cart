package cart.product.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {

    private final Long id;
    private final String name;
    private final String image;
    private final Integer price;

    public static ProductResponse of(final Long id, final String name, final String image_url, final Integer price) {
        return new ProductResponse(id, name, image_url, price);
    }
}
