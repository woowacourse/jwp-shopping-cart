package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.fixture.CustomerFixtures.CUSTOMER_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_2;

import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
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
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemResponses;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@JdbcTest
class CartServiceTest {
    private final CartService cartService;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    @Autowired
    CartServiceTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        productDao = new JdbcProductDao(jdbcTemplate);
        customerDao = new JdbcCustomerDao(jdbcTemplate, dataSource);
        final CartItemDao cartItemDao = new JdbcCartItemDao(jdbcTemplate);
        this.cartService = new CartService(cartItemDao, new ProductService(productDao));
    }

    @DisplayName("카트 아이템을 저장한다.")
    @Test
    public void addCart() {
        //given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId = productDao.save(PRODUCT_1);

        // when
        final Long id = cartService.addCart(customerId, productId, 3);

        // then
        assertThat(id).isPositive();
    }

    @DisplayName("카트 아이템을 조회한다.")
    @Test
    public void findCartsByCustomerId() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);
        final Long cartId1 = cartService.addCart(customerId, productId1, 3);
        final Long cartId2 = cartService.addCart(customerId, productId2, 4);

        // when
        final CartItemResponses cartResponses = cartService.findCartsByCustomerId(customerId);
        final List<ProductResponse> productResponses = cartResponses.getCart()
                .stream()
                .map(CartItemResponse::getProductResponse).collect(
                        Collectors.toList());
        final List<Integer> quantities = cartResponses.getCart().stream().map(CartItemResponse::getQuantity)
                .collect(Collectors.toList());
        // then
        assertAll(
                () -> assertThat(productResponses).extracting("name", "price", "imageUrl", "description", "stock")
                        .containsExactly(
                                tuple(PRODUCT_1.getName(), PRODUCT_1.getPrice(),
                                        PRODUCT_1.getImageUrl(), PRODUCT_1.getDescription(),
                                        PRODUCT_1.getStock()),
                                tuple(PRODUCT_2.getName(), PRODUCT_2.getPrice(),
                                        PRODUCT_2.getImageUrl(), PRODUCT_2.getDescription(),
                                        PRODUCT_2.getStock())
                        ),
                () -> assertThat(quantities).hasSize(2).containsExactly(3, 4)
        );
    }

    @DisplayName("cartItem 삭제 시 cartItem에 해당하는 customerId가 불일치할 시 예외가 발생한다.")
    @Test
    public void deleteByInvalidCustomerId() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartId1 = cartService.addCart(customerId, productId1, 3);

        // when & then
        assertThatThrownBy(() -> cartService.deleteCart(cartId1, customerId + 1))
                .isInstanceOf(NotInCustomerCartItemException.class)
                .hasMessage("장바구니 아이템이 없습니다.");
    }

    @DisplayName("cartItem을 삭제한다.")
    @Test
    public void deleteCart() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long cartId1 = cartService.addCart(customerId, productId1, 3);

        // when & then
        assertThatCode(() -> cartService.deleteCart(cartId1, customerId))
                .doesNotThrowAnyException();
    }

    @DisplayName("product가 존재하는 지 확인한다.")
    @Test
    public void hasProduct() {
        // given
        final int customerId = customerDao.save(CUSTOMER_1);
        final Long productId1 = productDao.save(PRODUCT_1);
        final Long productId2 = productDao.save(PRODUCT_2);
        cartService.addCart(customerId, productId1, 3);

        // when
        boolean result1 = cartService.hasProduct(customerId, productId1);
        boolean result2 = cartService.hasProduct(customerId, productId2);


        // then
        assertAll(
                () -> assertThat(result1).isTrue(),
                () -> assertThat(result2).isFalse()
        );
    }
}