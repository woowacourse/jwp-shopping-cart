package cart.service;

import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.UserDao;
import cart.domain.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import cart.exception.cart.CartAlreadyExistsException;
import cart.exception.cart.CartNotFoundException;
import cart.exception.item.ItemNotFoundException;
import cart.exception.user.UserNotFoundException;
import cart.service.dto.CartDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private static final int CORRECT_RECORD_COUNT = 1;

    private final CartDao cartDao;
    private final ItemDao itemDao;
    private final UserDao userDao;

    public CartService(final CartDao cartDao, final ItemDao itemDao, final UserDao userDao) {
        this.cartDao = cartDao;
        this.itemDao = itemDao;
        this.userDao = userDao;
    }

    @Transactional
    public CartDto add(String email, Long itemId) {
        validateExistsItem(email, itemId);

        User user = userDao.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 사용자입니다."));
        Item item = itemDao.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("일치하는 상품을 찾을 수 없습니다."));
        Cart cart = cartDao.insert(user, item);

        return CartDto.from(cart);
    }

    private void validateExistsItem(final String email, final Long itemId) {
        List<Cart> carts = cartDao.findByEmailAndItemId(email, itemId);

        if (!carts.isEmpty()) {
            throw new CartAlreadyExistsException("이미 장바구니에 존재하는 상품입니다.");
        }
    }

    public List<CartDto> findAllByEmail(String email) {
        return cartDao.findAllByEmail(email)
                .stream()
                .map(CartDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id, String email) {
        int deleteRecordCount = cartDao.delete(id, email);

        if (deleteRecordCount != CORRECT_RECORD_COUNT) {
            throw new CartNotFoundException("장바구니에 존재하지 않는 상품입니다.");
        }
    }
}
