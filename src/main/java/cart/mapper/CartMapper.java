package cart.mapper;

import cart.dao.ProductDao;
import cart.dto.request.CartRequest;
import cart.dto.response.CartResponse;
import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import cart.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {
    private final ProductMapper productMapper;
    private final ProductDao productDao;

    public CartMapper(ProductMapper productMapper, ProductDao productDao) {
        this.productMapper = productMapper;
        this.productDao = productDao;
    }

    public CartResponse entityToResponse(CartEntity entity) {
        Long productId = entity.getProductId();
        ProductEntity product = productDao.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("해당 상품을 찾을 수 없습니다." + System.lineSeparator() + "id : " + productId));
        return new CartResponse(entity.getId(), productMapper.entityToResponse(product), entity.getCount());
    }

    public CartEntity requestToCartEntity(Long memberId, CartRequest cartRequest) {
        return new CartEntity(null, memberId, cartRequest.getProductId(), cartRequest.getCount(), null, null);
    }
}
