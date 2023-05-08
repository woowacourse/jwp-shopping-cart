package cart.service;

import cart.dao.CartDao;
import cart.dto.CartItemResponseDto;
import cart.exception.ExistProductException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductService productService;

    public CartService(CartDao cartDao, ProductService productService) {
        this.cartDao = cartDao;
        this.productService = productService;
    }

    public List<CartItemResponseDto> findAll(String memberEmail) {
        return cartDao.findAll(memberEmail).stream()
                .map(CartItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public int add(int productId, String memberEmail) {
        validateExistProduct(productId);
        List<Integer> cartIdByProductId = cartDao.findCartIdByProductId(productId, memberEmail);

        if (!cartIdByProductId.isEmpty()) {
            throw new ExistProductException("이미 장바구니에 담은 상품입니다.");
        }

        return cartDao.save(productId, memberEmail);
    }

    public void remove(int cartId) {
        validateExistCartItem(cartId);
        cartDao.deleteById(cartId);
    }

    private void validateExistProduct(int productId) {
        productService.validateExistProduct(productId);
    }

    private void validateExistCartItem(int cartId) {
        cartDao.findById(cartId)
                        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 cart id 입니다."));
    }

}
