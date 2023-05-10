package cart.service;

import cart.dto.CartItemDto;
import cart.entity.Cart;
import cart.entity.Member;
import cart.exception.customexceptions.DataNotFoundException;
import cart.repository.dao.cartDao.CartDao;
import cart.repository.dao.memberDao.MemberDao;
import cart.repository.dao.productDao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final MemberDao memberDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<CartItemDto> findAllCartItemsByEmail(final String email) {
        final Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        final List<CartItemDto> cartItems = cartDao.findAllCartItemsByMemberId(member.getId());

        return cartItems;
    }

    public Long addProductInCart(final Long productId, final String email) {
        final Member member = memberDao.findByEmail(email)
                .orElseThrow(() -> new DataNotFoundException("해당 사용자가 존재하지 않습니다."));
        final Cart cart = new Cart(member.getId(), productId);

        return cartDao.save(cart);
    }

    public void deleteProductByCartId(final Long cartId) {
        final int amountOfDeletedCart = cartDao.deleteByCartId(cartId);
        if (amountOfDeletedCart == 0) {
            throw new DataNotFoundException("해당 상품을 찾을 수 없습니다.");
        }
    }
}
