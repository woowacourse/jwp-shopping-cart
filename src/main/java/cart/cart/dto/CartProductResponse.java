package cart.cart.dto;

import cart.product.dto.ProductResponse;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class CartProductResponse {
    private final Long cartId;
    private final String name;
    private final String imageUrl;
    private final Integer price;
    
    private CartProductResponse(final Long cartId, final String name, final String imageUrl, final Integer price) {
        this.cartId = cartId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }
    
    public static CartProductResponse from(final Long cartId, final ProductResponse product) {
        return new CartProductResponse(cartId, product.getName(), product.getImageUrl(), product.getPrice());
    }
}
