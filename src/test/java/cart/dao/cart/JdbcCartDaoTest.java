package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cart.dao.entity.Product;
import cart.dao.entity.User;

@JdbcTest
class JdbcCartDaoTest {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new JdbcCartDao(namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("회원이 장바구니에 담은 아이템 목록을 반환한다.")
    void findAllProductInCart() {
        final User user = new User("ahdjd5@gmail.com", "qwer1234");

        final List<Product> result = cartDao.findAllProductInCart(user);

        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000)
        );
    }
}
