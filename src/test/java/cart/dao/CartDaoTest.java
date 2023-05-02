package cart.dao;

import cart.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CartDaoTest {

    private final CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDaoTest(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO product (name, price, image_url) " +
                "VALUES ('product1', 100, 'url.com')");

        jdbcTemplate.update("INSERT INTO cart (user_id, product_id) " +
                "VALUES (1, 1), (1, 1)");

        jdbcTemplate.update("INSERT INTO cart (user_id, product_id) " +
                "VALUES (2, 1)");
    }

    @DisplayName("1번 유저의 장바구니를 모두 조회한다.")
    @Test
    void findAllOfMember1() {
        final List<Product> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(2);
    }
}
