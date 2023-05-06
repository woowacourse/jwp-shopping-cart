package cart.service;

import static java.util.stream.Collectors.toList;

import cart.dao.CartProductDao;
import cart.domain.CartProduct;
import cart.dto.request.CartProductRequest;
import cart.dto.response.CartProductResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartProductService {

    private final CartProductDao cartProductDao;

    public CartProductService(CartProductDao cartProductDao) {
        this.cartProductDao = cartProductDao;
    }

    @Transactional
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

    @Transactional
    public void deleteById(Long cartProductId) {
        cartProductDao.deleteById(cartProductId);
    }
}
