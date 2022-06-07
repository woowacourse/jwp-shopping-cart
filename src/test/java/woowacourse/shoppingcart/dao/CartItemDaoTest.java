package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.CartProductResponse;

import javax.sql.DataSource;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItemDaoTest(DataSource dataSource) {
        cartItemDao = new CartItemDao(dataSource);
        productDao = new ProductDao(dataSource);
    }

    @DisplayName("장바구니 물건 추가")
    @Test
    void addCartItem() {
        // given
        Product product = productDao.save(new Product("apple", 2_000, "woowa2.com"));

        // when
        final Long cartId = cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        // then
        assertThat(cartId).isNotNull();
    }

    @DisplayName("카트 아이템 번호로 아이템을 찾는다. ")
    @Test
    void findProductIdsByCustomerId() {
        // given
        Product product = productDao.save(new Product("apple", 1000, "woowa2.com"));
        final Long cartItemId = cartItemDao.addCartItem(1L, product.getId(), 1L, true);

        // when
        CartProductResponse cartProductResponse = cartItemDao.findCartIdById(cartItemId);

        // then
        assertThat(cartProductResponse.getQuantity()).isEqualTo(1);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {

        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long customerId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds).containsExactly(2L);
    }
}