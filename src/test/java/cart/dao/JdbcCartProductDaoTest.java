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
class JdbcCartProductDaoTest {

    private JdbcCartProductDao jdbcCartProductDao;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void init() {
        jdbcCartProductDao = new JdbcCartProductDao(jdbcTemplate);
        cartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    @Sql(value = { "jdbcCartProductInitializer.sql"})
    void 장바구니_전체_조회를_한다() {
        // given
        final long firstUserId = 1L;
        final long secondUserId = 2L;

        final List<CartProductResultMap> firstUserCart = jdbcCartProductDao.findAllByUserId(firstUserId);
        final List<CartProductResultMap> secondUserCart = jdbcCartProductDao.findAllByUserId(secondUserId);

        // then
        assertAll(
                () -> assertThat(firstUserCart).hasSize(2),
                () -> assertThat(secondUserCart).hasSize(3)
        );
    }
}
