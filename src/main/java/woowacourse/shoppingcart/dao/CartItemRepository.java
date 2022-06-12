package woowacourse.shoppingcart.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import woowacourse.shoppingcart.dao.dto.cartitem.CartItemDto;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.exception.NoSuchProductException;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemRepository(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public List<CartItem> findCartItemsByCustomerId(Long customerId) {
        List<CartItemDto> cartItemDtos = cartItemDao.findCartItemsByCustomerId(customerId);
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDto cartItemDto : cartItemDtos) {
            Product product = productDao.findProductById(cartItemDto.getProductId())
                    .orElseThrow(NoSuchProductException::new);
            cartItems.add(new CartItem(cartItemDto.getId(), product, cartItemDto.getQuantity()));
        }
        return cartItems;
    }
}
