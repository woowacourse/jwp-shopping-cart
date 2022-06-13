package woowacourse.shoppingcart.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<Long, Product> products = getProducts(cartItemDtos).stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
        return getCartItems(cartItemDtos, products);
    }

    private List<Product> getProducts(List<CartItemDto> cartItemDtos) {
        List<Long> productIds = cartItemDtos.stream()
                .map(CartItemDto::getProductId)
                .collect(Collectors.toList());
        List<Product> products = productDao.findProductsByIds(productIds);
        if (productIds.size() != products.size()) {
            throw new NoSuchProductException();
        }
        return products;
    }

    private List<CartItem> getCartItems(List<CartItemDto> cartItemDtos, Map<Long, Product> products) {
        List<CartItem> cartItems = new ArrayList<>();
        for (CartItemDto cartItemDto : cartItemDtos) {
            cartItems.add(new CartItem(cartItemDto.getId(), products.get(cartItemDto.getProductId()),
                    cartItemDto.getQuantity()));
        }
        return cartItems;
    }
}
