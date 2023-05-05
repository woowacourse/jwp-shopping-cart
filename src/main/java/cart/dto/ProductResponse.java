package cart.dto;

import cart.domain.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;

    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice());
    }
}
