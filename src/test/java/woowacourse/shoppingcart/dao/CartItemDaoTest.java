package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CustomerDao customerDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        cartItemDao = new CartItemDao(jdbcTemplate, namedParameterJdbcTemplate);
        customerDao = new CustomerDao(jdbcTemplate, dataSource);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        customerDao.save(new Customer("email", "Pw12345!", "name", "010-1234-5678", "address"));
    }

    @DisplayName("카트에 아이템을 담는다.")
    @Test
    void addCartItem() {
        // given
        final Long customerId = 1L;
        final Long productId = 1L;
        Cart cart = new Cart(customerId, productId, 5);

        // when
        cartItemDao.addCartItem(cart);

        // then
        List<Long> idsByCustomerId = cartItemDao.findIdsByCustomerId(customerId);
        assertThat(idsByCustomerId).isEqualTo(List.of(1L));
    }

    @DisplayName("카트에 여러 아이템을 담고 모두 삭제한다.")
    @Test
    void deleteCartItems() {
        //given
        Long customerId = 1L;
        cartItemDao.addCartItem(new Cart(customerId, customerId, 5));
        cartItemDao.addCartItem(new Cart(customerId, 2L, 5));
        cartItemDao.addCartItem(new Cart(customerId, 3L, 5));

        //when
        cartItemDao.deleteCartItems(customerId, List.of(customerId, 2L, 3L));

        //then
        List<Long> idsByCustomerId = cartItemDao.findIdsByCustomerId(customerId);
        assertThat(idsByCustomerId.size()).isEqualTo(0);
    }

}
