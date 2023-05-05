package cart.domain.cartitem;

import cart.domain.product.ProductService;
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
}
