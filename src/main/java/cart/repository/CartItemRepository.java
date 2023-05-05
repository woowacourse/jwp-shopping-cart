package cart.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import cart.dao.CartItemDao;
import cart.dao.dto.CartItemDto;
import cart.domain.CartItem;
import cart.domain.User;

@Repository
public class CartItemRepository {

    private final CartItemDao cartItemDao;
    private final ProductRepository productRepository;

    public CartItemRepository(CartItemDao cartItemDao, ProductRepository productRepository) {
        this.cartItemDao = cartItemDao;
        this.productRepository = productRepository;
    }

    public void save(Integer userId, List<CartItem> cartItems) {
        cartItemDao.deleteAllItemsOf(userId);

        List<Integer> productIds = cartItems.stream()
                .map(cartItem -> cartItem.getProduct().getId())
                .collect(Collectors.toList());

        cartItemDao.insertAll(userId, productIds);
    }

    public List<CartItem> getItemsOf(User user) {
        final List<CartItemDto> cartItemDtos = cartItemDao.selectAllItemsOf(user.getId());
        return getItemsBy(cartItemDtos);
    }

    public CartItem findBy(Integer id) {
        return getItemBy(cartItemDao.selectBy(id));
    }

    private List<CartItem> getItemsBy(List<CartItemDto> cartItemDtos) {
        return cartItemDtos.stream()
                .map(this::getItemBy)
                .collect(Collectors.toList());
    }

    private CartItem getItemBy(CartItemDto cartItemDto) {
        return new CartItem(
                cartItemDto.getId(),
                productRepository.findBy(cartItemDto.getProductId())
        );
    }
}
