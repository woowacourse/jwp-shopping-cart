package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

	@Mock
	private CartItemDao cartItemDao;
	@Mock
	private CustomerDao customerDao;
	@Mock
	private ProductDao productDao;
	@InjectMocks
	private CartService cartService;

	@DisplayName("장바구니에 상품을 추가한다.")
	@Test
	void addCart() {
		// given
		long customerId = 1L;
		long productId = 1L;
		long cartItemId = 1L;
		int quantity = 2;

		Product product = new Product(productId, "치킨", 20000, "test.jpg");
		given(productDao.findById(productId))
			.willReturn(product);
		given(cartItemDao.save(eq(customerId), any(CartItem.class)))
			.willReturn(new CartItem(product, quantity).createWithId(cartItemId));

		// when
		CartItem cartItem = cartService.addItem(customerId, productId, quantity);

		// then
		assertAll(
			() -> assertThat(cartItem.getId()).isEqualTo(cartItemId),
			() -> assertThat(cartItem.getQuantity()).isEqualTo(quantity),
			() -> verify(productDao).findById(productId),
			() -> verify(cartItemDao).save(eq(customerId), any(CartItem.class))
		);
	}
}
