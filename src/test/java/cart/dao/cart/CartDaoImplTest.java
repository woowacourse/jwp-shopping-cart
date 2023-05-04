package cart.dao.cart;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import cart.entity.cart.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void setting() {
        cartDao = new CartDaoImpl(jdbcTemplate);
    }

    @Test
    @DisplayName("")
    void insert_cart() {
        // given
        jdbcTemplate.execute("INSERT INTO member(email, password) values ('ako@naver.com', 'ako')");
        jdbcTemplate.execute("INSERT INTO product(name, image_url, price) values ('연필', '이미지', 1000)");
        Cart cart = new Cart(1L, 1L, 2);

        // when
        Long result = cartDao.insertCart(cart);

        // then
        assertThat(result).isEqualTo(1L);
    }

}