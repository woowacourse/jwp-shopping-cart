package cart.service.cart;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cart.dao.cart.CartDao;
import cart.dao.cart.dto.CartProductDto;
import cart.domain.cart.Cart;
import cart.service.cart.dto.CartDto;

@Service
@Transactional
public class CartService {

	private final CartDao cartDao;

	public CartService(final CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public CartDto addProduct(final Long userId, final Long productId) {
		final Cart cart = new Cart(userId, productId);
		final Cart savedCart = cartDao.save(cart);

		return mapCartToCartDto(savedCart);
	}

	@Transactional(readOnly = true)
	public List<CartProductDto> findByEmail(final String email) {
		return cartDao.findProductByEmail(email);
	}

	public CartDto updateQuantity(final Long userId, final Long productId) {
		final CartProductDto cartProductDto = cartDao.findByIds(userId, productId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		cartDao.updateByCartId(cartProductDto.getId());
		final Cart updatedCart = new Cart(cartProductDto.getId(), userId, productId, cartProductDto.getQuantity() + 1);

		return mapCartToCartDto(updatedCart);
	}

	public void deleteProduct(final Long userId, final Long productId) {
		final CartProductDto cartProductDto = cartDao.findByIds(userId, productId)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 없습니다."));

		cartDao.deleteByCartId(cartProductDto.getId());
	}

	private CartDto mapCartToCartDto(final Cart cart) {
		return new CartDto(cart.getId(), cart.getUserId(), cart.getProductId(), cart.getQuantity());
	}
}
