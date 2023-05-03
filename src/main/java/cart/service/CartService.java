package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
<<<<<<< HEAD
import cart.controller.dto.request.ProductIdRequest;
import cart.controller.dto.response.CartItemResponse;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

=======
import cart.controller.dto.request.MemberRequest;
import cart.controller.dto.request.ProductIdRequest;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
@ServiceWithTransactionalReadOnly
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

<<<<<<< HEAD
    public CartService(CartDao cartDao, ProductDao productDao) {
=======
    public CartService(final CartDao cartDao, final ProductDao productDao) {
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional
    public void save(final Long memberId, final ProductIdRequest request) {
<<<<<<< HEAD
        cartDao.save(memberId, request.getProductId());
    }

    public List<CartItemResponse> findAll(Long memberId) {
        return cartDao.findAllByMemberId(memberId).stream()
                .map(CartItemResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long cartId) {
        cartDao.deleteById(cartId);
=======
        cartDao.create(memberId, request.getProductId());
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
    }

}
