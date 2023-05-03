package cart.domain.persistence.dao;

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

import cart.domain.persistence.ProductDto;
import cart.domain.persistence.entity.CartEntity;
import cart.domain.persistence.entity.ProductEntity;

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
        final CartEntity cartEntity = new CartEntity(1, 1);

        assertDoesNotThrow(() -> cartDao.save(cartEntity));
    }

    @Test
    void findAllByMemberId_메서드로_특정_사용자의_장바구니를_불러온다() {
        final ProductEntity chicken = new ProductEntity("chicken", 10000, "https://a.com");
        final ProductEntity pizza = new ProductEntity("pizza", 20000, "https://b.com");
        long chickenId = productDao.save(chicken);
        long pizzaId = productDao.save(pizza);
        final long memberId = 1L;
        final CartEntity cartEntity1 = new CartEntity(memberId, chickenId);
        final CartEntity cartEntity2 = new CartEntity(memberId, pizzaId);
        cartDao.save(cartEntity1);
        cartDao.save(cartEntity2);

        final List<ProductDto> products = cartDao.findAllByMemberId(memberId);

        assertThat(products.size()).isEqualTo(2);
    }
}
