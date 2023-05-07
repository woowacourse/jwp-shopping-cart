package cart.dao;

import cart.JdbcMySqlDialectTest;
import cart.dao.dto.CartProductResultMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcMySqlDialectTest
@SuppressWarnings("NonAsciiCharacters")
class CartProductDaoTest {

    private CartProductDao cartProductDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        cartProductDao = new CartProductDao(jdbcTemplate);
    }

    @Test
    @Sql(value = {"classpath:dataTruncator.sql", "classpath:jdbcTestInitializer.sql"})
    void 장바구니_전체_조회를_한다() {
        // given
        final long firstUserId = 1L;
        final long secondUserId = 2L;

        final List<CartProductResultMap> firstUserCart = cartProductDao.findAllByUserId(firstUserId);
        final List<CartProductResultMap> secondUserCart = cartProductDao.findAllByUserId(secondUserId);

        // then
        assertAll(
                () -> assertThat(firstUserCart).hasSize(2),
                () -> assertThat(secondUserCart).hasSize(3)
        );
    }
}
