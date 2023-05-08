package cart.persistence.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import cart.persistence.CartProduct;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.JdbcCartDao;
import cart.persistence.dao.JdbcProductDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Cart;
import cart.persistence.entity.Product;

@JdbcTest
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcCartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new JdbcCartDao(jdbcTemplate);
        this.productDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    void save_메서드로_상품을_저장한다() {
        final Cart cart = new Cart(1, 1);

        assertDoesNotThrow(() -> cartDao.save(cart));
    }

    @Test
    void findAllByMemberId_메서드로_특정_사용자의_장바구니를_불러온다() {
        final Product chicken = new Product("chicken", 10000, "https://a.com");
        final Product pizza = new Product("pizza", 20000, "https://b.com");
        long chickenId = productDao.save(chicken);
        long pizzaId = productDao.save(pizza);
        final long memberId = 1L;
        final Cart cart1 = new Cart(memberId, chickenId);
        final Cart cart2 = new Cart(memberId, pizzaId);
        cartDao.save(cart1);
        cartDao.save(cart2);

        final List<CartProduct> products = cartDao.findAllByMemberId(memberId);

        assertThat(products.size()).isEqualTo(2);
    }
}
