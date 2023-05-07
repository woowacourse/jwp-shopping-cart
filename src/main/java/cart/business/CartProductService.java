package cart.business;

import cart.entity.CartProduct;
import cart.persistence.CartProductDao;
import cart.presentation.dto.CartProductRequest;
import cart.presentation.dto.CartProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartProductService {

    private CartProductDao cartProductDao;

    public CartProductService(CartProductDao cartProductDao) {
        this.cartProductDao = cartProductDao;
    }

    @Transactional
    public Integer create(CartProductRequest request) {

        CartProduct cartProduct = makeCartProductFromRequest(request);

        return cartProductDao.insert(cartProduct);
    }

    @Transactional(readOnly = true)
    public List<CartProduct> read() {
        return cartProductDao.findAll();
    }

    @Transactional
    public Integer delete(Integer id) {
        return cartProductDao.remove(id);
    }

    private CartProduct makeCartProductFromRequest(CartProductRequest request) {
        return new CartProduct(null, request.getProductId(), request.getCartId());
    }

    private CartProduct makeCartProductFromResponse(CartProductResponse response) {
        return new CartProduct(response.getId(), response.getProductId(), response.getCartId());
    }
}
