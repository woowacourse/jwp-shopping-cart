package cart.domain.cartitem;

import cart.domain.product.ProductService;
import cart.dto.CartItemDto;
import cart.exception.MemberForbiddenException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartItemService(final CartItemDao cartItemDao, final ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public void add(CartItem cartItem) {
        productService.validateIdExist(cartItem.getProductId());
        validateDuplicatedItem(cartItem);
        cartItemDao.insert(cartItem);
    }

    private void validateDuplicatedItem(CartItem cartItem) {
        if (cartItemDao.isDuplicated(cartItem.getMemberId(), cartItem.getProductId())) {
            throw new IllegalArgumentException("이미 장바구니에 담은 상품입니다");
        }
    }

    public List<CartItemDto> findAllByMemberId(final Long id) {
        return cartItemDao.findByMemberId(id);
    }

    public void deleteById(final Long memberId, final Long id) {
        validateIds(memberId, id);
        cartItemDao.deleteById(id);
    }

    private void validateIds(final Long memberId, final Long id) {
        CartItem cartItem = cartItemDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id입니다. value: " + id));

        if (memberId != cartItem.getMemberId()) {
            throw new MemberForbiddenException("사용자 정보와 장바구니 아이템 정보가 일치하지 않습니다.");
        }
    }
}
