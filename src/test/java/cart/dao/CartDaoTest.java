package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.dao.CartDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {

    CartDao cartDao;

    @BeforeEach
    void setUp(@Autowired JdbcTemplate jdbcTemplate) {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원의 장바구니를 조회한다.")
    void findByEmailSuccessWithExistsCart() {
        Long actual = cartDao.findByUserId(1L);

        assertThat(actual).isPositive();
    }
}
