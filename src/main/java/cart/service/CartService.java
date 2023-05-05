package cart.service;

import cart.dao.CartDao;
import cart.dao.MemberDao;
import cart.dao.ProductDao;
import cart.dto.response.CartProductResponse;
import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import cart.exception.CartNotFoundException;
import cart.exception.CartOwnerException;
import cart.exception.ProductNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final MemberDao memberDao;

    public CartService(final CartDao cartDao, final ProductDao productDao, final MemberDao memberDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.memberDao = memberDao;
    }

    @Transactional(readOnly = true)
    public List<CartProductResponse> findCartItemsForMember(final Long memberId) {
        final List<CartEntity> carts = cartDao.findAllByMemberId(memberId);
        return carts.stream()
                .map(cart -> {
                    final ProductEntity product = getProduct(cart.getProductId());
                    return CartProductResponse.from(cart.getId(), product);
                })
                .collect(Collectors.toList());
    }

    private ProductEntity getProduct(final Long productId) {
        return productDao.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("등록되지 않은 상품입니다."));
    }

    @Transactional
    public Long putInCart(final Long productId, final Long memberId) {
        final ProductEntity product = getProduct(productId);
        return cartDao.save(new CartEntity(memberId, product.getId()));
    }

    @Transactional
    public void removeCartItem(final Long cartId, final Long memberId) {
        final CartEntity cart = getCart(cartId);
        if (!cart.isOwner(memberId)) {
            throw new CartOwnerException("장바구니 상품 소유자가 아닙니다.");
        }
        cartDao.delete(cartId);
    }

    private CartEntity getCart(final Long cartId) {
        return cartDao.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("등록되지 않은 장바구니 상품입니다."));
    }
}
