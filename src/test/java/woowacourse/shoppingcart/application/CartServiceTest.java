package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.exception.InvalidCartItemException;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

	@Mock
	private CartItemDao cartItemDao;
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

		Product product = new Product(productId, "치킨", 20000, "https://test.jpg");
		given(productDao.findById(productId))
			.willReturn(product);
		given(cartItemDao.save(eq(customerId), any(CartItem.class)))
			.willReturn(new CartItem(product, quantity).createWithId(cartItemId));

		// when
		CartItem cartItem = cartService.setItem(customerId, productId, quantity);

		// then
		assertAll(
			() -> assertThat(cartItem.getId()).isEqualTo(cartItemId),
			() -> assertThat(cartItem.getQuantity()).isEqualTo(quantity),
			() -> verify(productDao).findById(productId),
			() -> verify(cartItemDao).save(eq(customerId), any(CartItem.class)),
			() -> verify(cartItemDao).findByCustomerId(customerId),
			() -> verify(cartItemDao, never()).update(any(CartItem.class))
		);
	}

	@DisplayName("존재하는 장바구니 상품의 수량을 변경한다.")
	@Test
	void updateItem() {
		// given
		long customerId = 1L;
		long productId = 1L;
		long cartItemId = 1L;
		int quantity = 2;

		Product product = new Product(productId, "치킨", 20000, "https://test.jpg");
		CartItem existItem = new CartItem(cartItemId, product, 3);
		given((cartItemDao.findByCustomerId(customerId)))
			.willReturn(List.of(existItem));

		// when
		CartItem cartItem = cartService.setItem(customerId, productId, quantity);

		// then
		assertAll(
			() -> assertThat(cartItem.getId()).isEqualTo(cartItemId),
			() -> assertThat(cartItem.getQuantity()).isEqualTo(quantity),
			() -> verify(productDao, never()).findById(productId),
			() -> verify(cartItemDao).update(existItem),
			() -> verify(cartItemDao, never()).save(eq(customerId), any(CartItem.class))
		);
	}

	@DisplayName("customer id로 장바구니를 조회한다.")
	@Test
	void findItemsByCustomer() {
		// given
		given(cartItemDao.findByCustomerId(1L))
			.willReturn(List.of());

		// when
		cartService.findItemsByCustomer(1L);

		// then
		verify(cartItemDao).findByCustomerId(1L);
	}

	@DisplayName("장바구니 안에 있는 상품을 상품 id로 조회한다.")
	@ParameterizedTest
	@ValueSource(strings = {"1,2", "1", "2", "1,3", "1,2,3", "3"})
	void findByProductIdsInCart(String input) {
		// given
		List<Long> ids = convertToLong(input);
		long customerId = 1L;

		Product product1 = new Product(1L, "치킨", 20000, "https://test.com");
		Product product2 = new Product(2L, "콜라", 1500, "https://test.com");
		Product product3 = new Product(3L, "피자", 15000, "https://test.com");
		// given
		given(cartItemDao.findByCustomerId(customerId))
			.willReturn(List.of(
				new CartItem(1L, product1, 2),
				new CartItem(2L, product2, 3),
				new CartItem(3L, product3, 4)
			));

		// when
		List<CartItem> findItems = cartService.findItemsByProductIdsInCart(customerId, ids);

		// then
		List<Long> resultIds = findItems.stream()
			.map(CartItem::getProductId)
			.collect(Collectors.toList());
		assertThat(resultIds.containsAll(ids)).isTrue();
	}

	@DisplayName("삭제할 상품 id로 장바구니 상품을 전부 삭제한다.")
	@ParameterizedTest
	@ValueSource(strings = {"1,2", "1", "2"})
	void deleteAllItems(String inputs) {
		List<Long> ids = convertToLong(inputs);

		long customerId = 1L;

		Product product1 = new Product(1L, "치킨", 20000, "https://test.com");
		Product product2 = new Product(2L, "콜라", 1500, "https://test.com");
		// given
		given(cartItemDao.findByCustomerId(customerId))
			.willReturn(List.of(
				new CartItem(1L, product1, 2),
				new CartItem(2L, product2, 3))
			);

		// when
		cartService.deleteItems(customerId, ids);

		// then
		assertAll(
			() -> verify(cartItemDao).findByCustomerId(customerId),
			() -> verify(cartItemDao).deleteAll(ids)
		);
	}

	@DisplayName("장바구니에 없는 상품 id로 삭제하려 하면 예외가 발생한다.")
	@ParameterizedTest
	@ValueSource(strings = {"1,2,3", "3", "1,3"})
	void deleteAllItemsException(String input) {
		List<Long> ids = convertToLong(input);

		long customerId = 1L;

		Product product1 = new Product(1L, "치킨", 20000, "https://test.com");
		Product product2 = new Product(2L, "콜라", 1500, "https://test.com");
		// given
		given(cartItemDao.findByCustomerId(customerId))
			.willReturn(List.of(
				new CartItem(1L, product1, 2),
				new CartItem(2L, product2, 3))
			);

		// when
		assertThatThrownBy(() -> cartService.deleteItems(customerId, ids))
			.isInstanceOf(InvalidCartItemException.class);

		// then
		assertAll(
			() -> verify(cartItemDao).findByCustomerId(customerId),
			() -> verify(cartItemDao, never()).deleteAll(any())
		);
	}

	private List<Long> convertToLong(String inputs) {
		return Arrays.stream(inputs.split(","))
			.map(Long::parseLong)
			.collect(Collectors.toList());
	}
}
