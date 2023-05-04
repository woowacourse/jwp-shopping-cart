package cart.repository.dao.cartDao;

import cart.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcCartDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcCartDao jdbcCartDao;

    @BeforeEach
    void setUp() {
        jdbcCartDao = new JdbcCartDao(dataSource);
    }

    @Test
    void 장바구니에_저장한다() {
        Long memberId = 1L;
        Long productId = 10L;
        Cart cart = new Cart(memberId, productId);

        Long id = jdbcCartDao.save(cart);

        assertThat(id).isPositive();
    }

    @Test
    void 회원ID로_장바구니를_가져온다() {
        Long memberId = 1L;
        Long productId = 10L;
        Cart cart = new Cart(memberId, productId);
        jdbcCartDao.save(cart);

        List<Long> products = jdbcCartDao.findAllProductIdByMemberId(memberId);

        assertThat(products.get(0)).isEqualTo(productId);
    }

    @Test
    void 회원ID와_상품ID로_장바구니_상품을_삭제한다() {
        int expected = 1;
        Long memberId = 1L;
        Long productId = 10L;
        Cart cart = new Cart(memberId, productId);
        jdbcCartDao.save(cart);


        int amountOfCartDeleted = jdbcCartDao.delete(memberId, productId);

        assertThat(amountOfCartDeleted).isEqualTo(expected);
    }
}
