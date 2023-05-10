package cart.dto.cart;

import org.springframework.stereotype.Component;

import cart.dao.entity.CartProduct;

@Component
public class CartProductMapper {
    public CartProductResponse toResponse(CartProduct cartProduct) {
        return new CartProductResponse(
                cartProduct.getId(),
                cartProduct.getName(),
                cartProduct.getPrice(),
                cartProduct.getImgUrl()
        );
    }
}
