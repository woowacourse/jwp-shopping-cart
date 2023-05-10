package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cart.dao.entity.CartProduct;
import cart.dao.entity.Product;
import cart.dao.entity.User;
import cart.dao.product.JdbcProductDao;
import cart.dao.product.ProductDao;
import cart.dao.user.JdbcUserDao;
import cart.dao.user.UserDao;

@JdbcTest
class JdbcCartDaoTest {

    public static final User USER = new User("ahdjd5@gmail.com", "qwer1234");
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    CartDao cartDao;
    ProductDao productDao;
    UserDao userDao;
    Long productId;

    @BeforeEach
    void setUp() {
        cartDao = new JdbcCartDao(namedParameterJdbcTemplate);
        productDao = new JdbcProductDao(namedParameterJdbcTemplate);
        userDao = new JdbcUserDao(namedParameterJdbcTemplate);
        userDao.saveUser(USER);
        productId = productDao.save(new Product("치킨", 10000, "img"));
    }

    @Test
    @DisplayName("회원이 장바구니에 담은 아이템 목록을 반환한다.")
    void findAllProductInCart() {
        cartDao.addProduct(USER, productId);

        final List<CartProduct> result = cartDao.findAllProductInCart(USER);

        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000)
        );
    }

    @Test
    @DisplayName("회원이 장바구니에 담아놓은 아이템을 삭제한다.")
    void removeProductInCart() {
        final Long cartId = cartDao.addProduct(USER, productId);
        cartDao.removeProductInCart(USER, cartId);

        final List<CartProduct> results = cartDao.findAllProductInCart(USER);

        assertThat(results.stream()
                .anyMatch(product -> Objects.equals(product.getId(), productId)))
                .isFalse();
    }
}
