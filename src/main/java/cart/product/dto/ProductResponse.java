package cart.product.dto;

import cart.product.domain.ImageUrl;
import cart.product.domain.Name;
import cart.product.domain.Price;
import cart.product.domain.Product;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
public class ProductResponse {
    private final Long id;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    
    @Builder
    public ProductResponse(final Long id, final String name, final String imageUrl, final Integer price) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    
    public static ProductResponse from(Product product) {
        final Name name = product.getName();
        final Price price = product.getPrice();
        final ImageUrl imageUrl = product.getImageUrl();
        return new ProductResponse(product.getId(), name.getName(), imageUrl.getUrl(), price.getPrice());
    }
}
