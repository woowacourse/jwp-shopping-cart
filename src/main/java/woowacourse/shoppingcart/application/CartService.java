package woowacourse.shoppingcart.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    @Autowired
    public CartService(CartItemDao cartItemDao, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public void addCart(long memberId, long productId, int quantity) {
        checkStock(productId, quantity);
        if (cartItemDao.isExistsMemberIdAndProductId(memberId, productId)) {
            cartItemDao.increaseQuantity(memberId, productId, quantity);
            return;
        }
        cartItemDao.addCartItem(memberId, productId, quantity);
    }

    private void checkStock(long productId, int quantity) {
        if (productService.isImpossibleQuantity(productId, quantity)) {
            throw new IllegalArgumentException("상품 재고가 부족합니다.");
        }
    }
}
