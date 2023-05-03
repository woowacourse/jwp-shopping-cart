package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dto.CartDto;
import cart.dto.CartResponseDto;
import cart.dto.ProductResponseDto;
import cart.entity.Cart;
import cart.vo.Email;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void save(CartDto cartDto) {
        cartDao.save(cartDto.toEntity());
    }

    public CartResponseDto toResponseDto(Cart cart) {
        return new CartResponseDto(
                cart.getId(),
                cart.getEmail(),
                new ProductResponseDto(productDao.findById(cart.getProductId()))
        );
    }

    public Cart findById(Long id) {
        return cartDao.findById(id);
    }

    public List<CartResponseDto> findAll(Email email) {
        return cartDao.selectAll(email.getValue())
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public void removeById(Long id, Email email) {
        Cart cart = findById(id);

        if (cart.canUserWithThisEmailBeDeleted(email)) {
            cartDao.deleteById(id);
            return;
        }

        throw new IllegalStateException("삭제할 권한이 없습니다.");
    }

}
