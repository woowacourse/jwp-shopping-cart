package cart.service.cart;

import cart.service.cart.domain.CartItems;
import cart.service.cart.dto.DeleteCartITemRequest;
import cart.service.cart.dto.InsertCartItemRequest;
import cart.service.cart.dto.ProductResponse;
import cart.service.member.MemberDao;
import cart.service.member.domain.Member;
import cart.service.product.ProductDao;
import cart.service.product.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, MemberDao memberDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Long createCartItem(InsertCartItemRequest InsertCartItemRequest) {
        Member member = memberDao.findByEmail(InsertCartItemRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        Product product = productDao.findById(InsertCartItemRequest.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품입니다.")
        );

        return cartDao.addCartItem(product.getId(), member.getId());

    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProductsByUserIdOnCart(InsertCartItemRequest InsertCartItemRequest) {
        Member member = memberDao.findByEmail(InsertCartItemRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        CartItems cartItems = cartDao.findCartItemsByMemberId(member.getId());
        return cartItems.toProductResponse();
    }

    public void deleteCartItem(DeleteCartITemRequest deleteCartITemRequest) {
        Member member = memberDao.findByEmail(deleteCartITemRequest.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        Long cartId = cartDao.findOneCartItem(member.getId(), deleteCartITemRequest.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 상품은 삭제할 수 없습니다.")
        );
        cartDao.deleteCartItem(cartId);
    }
}
