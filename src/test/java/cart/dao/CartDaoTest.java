package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.dao.CartDao;
import java.util.Optional;
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
    @DisplayName("회원의 장바구니를 생성한다.")
    void insertSuccess() {
        Long actual = cartDao.insert("a@a.com");

        assertThat(actual).isPositive();
    }

    @Test
    @DisplayName("장바구니가 있는 회원의 장바구니를 조회한다.")
    void findByEmailSuccessWithNotExistsCart() {
        cartDao.insert("b@b.com");

        Optional<Long> actual = cartDao.findByEmail("b@b.com");

        assertThat(actual).isPresent();
    }

    @Test
    @DisplayName("장바구니가 없는 회원의 장바구니를 조회한다.")
    void findByEmailSuccessWithExistsCart() {
        Optional<Long> actual = cartDao.findByEmail("c@c.com");

        assertThat(actual).isEmpty();
    }
}
