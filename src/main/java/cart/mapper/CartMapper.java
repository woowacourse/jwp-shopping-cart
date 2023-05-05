package cart.mapper;

import cart.domain.Cart;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    private final ProductMapper productMapper;

    public CartMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Cart requestToCart(CartRequest request) {
        return new Cart(request.getProductId(), request.getCount());
    }

    public Cart entityToCart(CartEntity entity) {
        return new Cart(entity.getProduct().getId(), entity.getCount());
    }

    public CartResponse entityToResponse(CartEntity entity) {
        return new CartResponse(entity.getId(), productMapper.entityToResponse(entity.getProduct()), entity.getCount());
    }
}
