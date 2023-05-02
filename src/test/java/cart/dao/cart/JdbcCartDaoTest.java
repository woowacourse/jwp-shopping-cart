package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cart.dao.entity.Product;
import cart.dao.entity.User;

@JdbcTest
class JdbcCartDaoTest {

    public static final User USER = new User("ahdjd5@gmail.com", "qwer1234");
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new JdbcCartDao(namedParameterJdbcTemplate);
        namedParameterJdbcTemplate.update("DELETE FROM cart", new MapSqlParameterSource());
    }

    @Test
    @DisplayName("회원이 장바구니에 담은 아이템 목록을 반환한다.")
    void findAllProductInCart() {
        cartDao.addProduct(USER, 1L);

        final List<Product> result = cartDao.findAllProductInCart(USER);

        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000)
        );
    }

    @Test
    @DisplayName("회원이 장바구니에 담아놓은 아이템을 삭제한다.")
    void removeProductInCart() {
        final long productId = 1L;
        cartDao.addProduct(USER, productId);
        cartDao.removeProductInCart(USER, productId);

        final List<Product> results = cartDao.findAllProductInCart(USER);

        assertThat(results.stream()
                .anyMatch(product -> product.getId() == productId))
                .isFalse();
    }
}
