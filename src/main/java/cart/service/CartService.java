package cart.service;

import cart.dao.CartDao;
import cart.dto.CartItemResponseDto;
import cart.exception.ExistProductException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<CartItemResponseDto> findAll(String memberEmail) {
        return cartDao.findAll(memberEmail).stream()
                .map(CartItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public int add(int productId, String memberEmail) {
        List<Integer> cartIdByProductId = cartDao.findCartIdByProductId(productId, memberEmail);

        if (!cartIdByProductId.isEmpty()) {
            throw new ExistProductException("이미 장바구니에 담은 상품입니다.");
        }

        return cartDao.save(productId, memberEmail);
    }

    public void remove(int cartId) {
        cartDao.deleteById(cartId);
    }

}
