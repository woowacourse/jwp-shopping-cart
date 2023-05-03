package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
<<<<<<< HEAD
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
=======
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
import cart.controller.dto.request.ProductIdRequest;
import cart.dao.CartDao;
import org.springframework.transaction.annotation.Transactional;

>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
@ServiceWithTransactionalReadOnly
public class CartService {

    private final CartDao cartDao;

<<<<<<< HEAD
<<<<<<< HEAD
    public CartService(CartDao cartDao, ProductDao productDao) {
=======
    public CartService(final CartDao cartDao, final ProductDao productDao) {
>>>>>>> ed825fb4 (feat: 장바구니에 상품 추가)
=======
    public CartService(CartDao cartDao) {
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
        this.cartDao = cartDao;
    }

    @Transactional
    public void save(final Long memberId, final ProductIdRequest request) {
<<<<<<< HEAD
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
=======
        cartDao.save(memberId, request.getProductId());
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    }

}
