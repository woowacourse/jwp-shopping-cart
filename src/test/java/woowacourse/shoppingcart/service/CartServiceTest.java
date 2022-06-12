package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.Fixture.PRICE;
import static woowacourse.fixture.Fixture.PRODUCT_NAME;
import static woowacourse.fixture.Fixture.QUANTITY;
import static woowacourse.fixture.Fixture.TEST_EMAIL;
import static woowacourse.fixture.Fixture.TEST_PASSWORD;
import static woowacourse.fixture.Fixture.TEST_USERNAME;
import static woowacourse.fixture.Fixture.THUMBNAIL_URL;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.AddCartItemRequestDto;
import woowacourse.shoppingcart.dto.CartItemResponseDto;
import woowacourse.shoppingcart.dto.UpdateCartItemCountItemRequest;
import woowacourse.shoppingcart.exception.DuplicateCartItemException;
import woowacourse.shoppingcart.exception.NotFoundProductException;
import woowacourse.shoppingcart.exception.OverQuantityException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartServiceTest {

    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemDao cartItemDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ProductDao productDao;

    private Long customerId;
    private Long productId;

    @BeforeEach
    void setUp() {
        customerId = customerDao.save(Customer.createWithoutId(TEST_EMAIL, TEST_PASSWORD, TEST_USERNAME));
        productId = productDao.save(Product.createWithoutId(PRODUCT_NAME, PRICE, THUMBNAIL_URL, QUANTITY));
    }

    @Test
    @DisplayName("장바구니에 품목을 추가한다.")
    void addCart() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 1);
        cartService.addCartItem(addCartItemRequestDto, customerId);

        final List<CartItemResponseDto> cartItems = cartService.findCartsByCustomerId(customerId);

        assertThat(cartItems.size()).isEqualTo(1);
        assertThat(cartItems.get(0).getName()).isEqualTo(PRODUCT_NAME);
    }

    @Test
    @DisplayName("장바구니에 품목을 추가할때 이미 등록된 품목일 경우 예외가 발생한다.")
    void addCart_DuplicateProductException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 1);
        cartService.addCartItem(addCartItemRequestDto, customerId);

        assertThatThrownBy(() -> cartService.addCartItem(addCartItemRequestDto, customerId))
                .isInstanceOf(DuplicateCartItemException.class)
                .hasMessage("이미 담겨있는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니에 품목을 추가할때 재고가 주문수량보다 적으면 예외가 발생한다.")
    void addCart_OverQuantityException() {
        final AddCartItemRequestDto addCartItemRequestDto = new AddCartItemRequestDto(productId, 11);
        assertThatThrownBy(() -> cartService.addCartItem(addCartItemRequestDto, customerId))
                .isInstanceOf(OverQuantityException.class)
                .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("장바구니 내의 품목을 장바구니에서 제거한다.")
    void deleteCart() {
        cartItemDao.addCartItem(customerId, productId, 1);

        cartService.deleteCart(customerId, productId);

        final List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        assertThat(cartItems.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니 내의 품목의 수량을 수정한다.")
    void updateCart() {
        cartItemDao.addCartItem(customerId, productId, 1);

        cartService.updateCart(customerId, productId, new UpdateCartItemCountItemRequest(2));

        final CartItem cartItem = cartItemDao.findCartItemByCustomerIdAndProductId(customerId, productId).get();
        assertThat(cartItem.getCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("장바구니 내의 품목 수량을 수정할때 존재하지 않는 품목이면 예외가 발생한다.")
    void updateCart_NotFoundProductException() {
        cartItemDao.addCartItem(customerId, productId, 1);

        assertThatThrownBy(() -> cartService.updateCart(customerId, 2L, new UpdateCartItemCountItemRequest(2)))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("존재하지 않는 상품 ID입니다.");
    }

    @Test
    @DisplayName("장바구니 내의 품목 수량을 수정할때 재고가 주문수량보다 적을 경우 예외가 발생한다.")
    void updateCart_() {
        cartItemDao.addCartItem(customerId, productId, 1);

        assertThatThrownBy(() -> cartService.updateCart(customerId, productId, new UpdateCartItemCountItemRequest(11)))
                .isInstanceOf(OverQuantityException.class)
                .hasMessage("재고가 부족합니다.");
    }
}
