package cart.mapper;

import cart.domain.Cart;
import cart.dto.CartRequest;
import cart.dto.CartResponse;
import cart.entity.CartEntity;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    private final ProductMapper productMapper;
    private final MemberMapper memberMapper;

    public CartMapper(ProductMapper productMapper, MemberMapper memberMapper) {
        this.productMapper = productMapper;
        this.memberMapper = memberMapper;
    }

    public Cart requestToCart(CartRequest request) {
        return new Cart(request.getProductId(), request.getCount());
    }

    public CartResponse entityToResponse(CartEntity entity) {
        return new CartResponse(entity.getId(), memberMapper.entityToResponse(entity.getMember()), productMapper.entityToResponse(entity.getProduct()));
    }
}
