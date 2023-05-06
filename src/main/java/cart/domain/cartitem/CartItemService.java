package cart.domain.cartitem;

import cart.domain.product.ProductService;
import cart.dto.CartItemDto;
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

    public void deleteById(final Long id) {
        // TODO 사용자 아이디 비교, 불일치 시 Forbidden 응답하게 하기
        validateIdExist(id);
        cartItemDao.deleteById(id);
    }

    private void validateIdExist(Long id) {
        if (cartItemDao.isExist(id)) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 id입니다. value: " + id);
    }
}
