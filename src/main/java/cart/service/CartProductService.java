package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dto.request.CartProductRequest;
import cart.dto.response.CartProductResponse;
import cart.persistnece.dao.CartProductDao;
import cart.persistnece.entity.CartProduct;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CartProductService {

    private final CartProductDao cartProductDao;

    public CartProductService(CartProductDao cartProductDao) {
        this.cartProductDao = cartProductDao;
    }

    public void save(Long memberId, CartProductRequest productIdRequest) {
        cartProductDao.save(memberId, productIdRequest.getProductId());
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> findAllByMemberId(Long memberId) {
        List<CartProduct> cartProducts = cartProductDao.findAllByMemberId(memberId);
        return cartProducts.stream()
                .map(CartProductResponse::new)
                .collect(toList());
    }

    public void deleteById(Long cartProductId) {
        cartProductDao.deleteById(cartProductId);
    }
}
