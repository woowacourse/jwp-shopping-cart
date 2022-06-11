package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.Fixtures.EMAIL_VALUE_1;
import static woowacourse.Fixtures.PASSWORD_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_DESCRIPTION_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_NAME_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_PRICE_VALUE_1;
import static woowacourse.Fixtures.PRODUCT_STOCK_VALUE_1;
import static woowacourse.Fixtures.PROFILE_IMAGE_URL_VALUE_1;
import static woowacourse.Fixtures.TERMS_1;

import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.JdbcCartItemDao;
import woowacourse.shoppingcart.dao.JdbcCustomerDao;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.ProductExistingInCartResponse;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.notfound.CartItemNotFoundException;
import woowacourse.shoppingcart.exception.notfound.ProductNotFoundException;
import woowacourse.shoppingcart.repository.CartItemRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@JdbcTest
class CartServiceTest {
    private final CartService cartService;
    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductRepository productRepository;

    private Long customerId;
    private Long productId;

    @Autowired
    public CartServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        cartItemDao = new JdbcCartItemDao(jdbcTemplate, dataSource);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);

        productRepository = new ProductRepository(new JdbcProductDao(jdbcTemplate, dataSource));
        CartItemRepository cartItemRepository = new CartItemRepository(cartItemDao, productRepository);
        cartService = new CartService(cartItemRepository, productRepository);
    }

    @BeforeEach
    void setUp() {
        customerId = (long) customerDao.save(
                new CustomerEntity(EMAIL_VALUE_1, PASSWORD_VALUE_1, PROFILE_IMAGE_URL_VALUE_1, TERMS_1));

        productId = productRepository.save(
                new Product(PRODUCT_NAME_VALUE_1, PRODUCT_DESCRIPTION_VALUE_1, PRODUCT_PRICE_VALUE_1,
                        PRODUCT_STOCK_VALUE_1, PROFILE_IMAGE_URL_VALUE_1));
    }

    @DisplayName("카트 아이템 추가 요청시 제품을 찾을 수 없다면 예외가 발생한다.")
    @Test
    void addCartItem_productNotFound() {
        // when & then
        assertThatThrownBy(() -> cartService.addCart(new CartItemRequest(productId + 1, 10), customerId))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @DisplayName("유저의 특정 카트 아이템 수정")
    @Test
    void updateCartItem() {
        // given
        Long cartItemId = cartService.addCart(new CartItemRequest(productId, 10), customerId);

        // when
        cartService.updateCartItem(customerId, cartItemId, new CartItemRequest(productId, 100));
        Integer actual = cartItemDao.findById(cartItemId).getQuantity();

        // then
        assertThat(actual).isEqualTo(100);
    }

    @DisplayName("카트 아이템 수정 요청시 카트 아이템을 찾을 수 없다면 예외가 발생한다.")
    @Test
    void updateCartItem_cartItemNotFound() {
        // when & then
        assertThatThrownBy(() -> cartService.updateCartItem(customerId, 999L, new CartItemRequest(productId, 100)))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @DisplayName("카트 아이템 제거 요청시 카트 아이템을 찾을 수 없다면 예외가 발생한다.")
    @Test
    void deleteCartItem_cartItemNotFound() {
        // when & then
        assertThatThrownBy(() -> cartService.deleteCart(customerId, 999L))
                .isInstanceOf(CartItemNotFoundException.class);
    }

    @DisplayName("특정 유저가 특정 품목을 장바구니에 담았는지 확인")
    @Test
    void isProductExisting() {
        // given
        cartService.addCart(new CartItemRequest(productId, 10), customerId);

        // when
        ProductExistingInCartResponse actual = cartService.isProductExisting(customerId, productId);

        // then
        assertThat(actual.isExists()).isTrue();
    }
}
