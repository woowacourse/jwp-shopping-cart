package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.customer.CustomerId;
import woowacourse.shoppingcart.domain.product.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartDaoTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @DisplayName("장바구니에 저장되는 것을 확인한다.")
    @Test
    void save() {
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, new ProductId(1), new Quantity(5));
        final String sql = "select product_id from cart where customer_id = :customerId";
        final int result = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("customerId", customerId.getValue()), Integer.class);

        assertThat(result).isEqualTo(1);
    }

    @DisplayName("장바구니의 항목을 삭제하는 것을 확인한다.")
    @Test
    void delete() {
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, new ProductId(1), new Quantity(3));
        cartDao.save(customerId, new ProductId(2), new Quantity(5));
        cartDao.deleteCarts(customerId, List.of(new ProductId(1), new ProductId(2)));
        final String sql = "select count(*) from cart where customer_id = :customerId";
        final int result = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("customerId", 1L), Integer.class);

        assertThat(result).isEqualTo(0);
    }

    @DisplayName("Customer에 해당하는 모든 장바구니 목록을 가져오는지 확인한다.")
    @Test
    void getAllCartsBy() {
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, new ProductId(1), new Quantity(3));
        cartDao.save(customerId, new ProductId(2), new Quantity(5));
        List<Cart> carts = cartDao.getAllCartsBy(customerId);
        final int result = carts.size();

        assertThat(result).isEqualTo(2);
    }

    @DisplayName("Customer의 상품이 존재하는 것을 확인한다.")
    @Test
    void exists_true() {
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, new ProductId(1), new Quantity(3));
        final Boolean result = cartDao.exists(customerId, new ProductId(1));

        assertThat(result).isTrue();
    }

    @DisplayName("Customer의 상품이 존재하지 않는 것을 확인한다.")
    @Test
    void exists_false() {
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, new ProductId(1), new Quantity(3));
        final Boolean result = cartDao.exists(customerId, new ProductId(2));

        assertThat(result).isFalse();
    }

    @DisplayName("장바구니에 저장된 물품이 수정되는지 확인한다.")
    @Test
    void edit() {
        ProductId productId = new ProductId(1);
        CustomerId customerId = new CustomerId(1L);
        cartDao.save(customerId, productId, new Quantity(5));
        cartDao.edit(customerId, productId, new Quantity(3));
        final String sql = "select quantity from cart where customer_id = :customerId and product_id = :productId";
        MapSqlParameterSource query = new MapSqlParameterSource("customerId", customerId.getValue());
        query.addValue("productId", productId.getValue());
        final int result = jdbcTemplate.queryForObject(sql, query, Integer.class);

        assertThat(result).isEqualTo(3);
    }
}
