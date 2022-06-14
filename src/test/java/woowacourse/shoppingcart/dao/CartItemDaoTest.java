package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.entity.CartItemEntity;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.dao.entity.ProductEntity;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = "classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class CartItemDaoTest {

    private static final CustomerEntity CUSTOMER = new CustomerEntity(null, "yeonlog", "연로그",
            "ab12AB!@", "연로그네", "01011112222");
    private static final ProductEntity PRODUCT = new ProductEntity("orange", 2_000, "woowa2.com");

    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        customerDao = new CustomerDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        cartItemDao = new CartItemDao(jdbcTemplate);
    }

    @Test
    void 카트_아이템_저장() {
        // given
        Long customerId = customerDao.save(CUSTOMER);
        Long productId = productDao.save(PRODUCT);

        // when
        Long cartId = cartItemDao.save(new CartItemEntity(customerId, productId));

        // then
        Optional<CartItemEntity> cartItem = cartItemDao.findById(cartId);
        assertThat(cartItem).isPresent();
        assertThat(cartItem.get().getId()).isEqualTo(cartId);
    }

    @Test
    void 카트_id로_카트_조회() {
        // given
        Long customerId = customerDao.save(CUSTOMER);
        Long productId = productDao.save(PRODUCT);
        Long cartId = cartItemDao.save(new CartItemEntity(customerId, productId));

        // when
        Optional<CartItemEntity> cartItem = cartItemDao.findById(cartId);

        // then
        assertThat(cartItem).isPresent();
        assertThat(cartItem.get().getId()).isEqualTo(cartId);
    }

    @Test
    void 회원_id로_카트_목록_조회() {
        // given
        Long customerId = customerDao.save(CUSTOMER);
        Long productId = productDao.save(PRODUCT);
        cartItemDao.save(new CartItemEntity(customerId, productId));

        // when
        final List<CartItemEntity> cartItems = cartItemDao.findByCustomerId(customerId);

        // then
        assertThat(generateCustomerIds(cartItems)).containsOnly(customerId);
    }

    private List<Long> generateCustomerIds(List<CartItemEntity> products) {
        return products.stream()
                .map(CartItemEntity::getCustomerId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Test
    void 카트_삭제() {
        // given
        Long customerId = customerDao.save(CUSTOMER);
        Long productId = productDao.save(PRODUCT);
        Long cartId = cartItemDao.save(new CartItemEntity(customerId, productId));

        // when
        cartItemDao.delete(new CartItemEntity(customerId, productId));

        // then
        assertThat(cartItemDao.findById(cartId)).isEmpty();
    }
}
