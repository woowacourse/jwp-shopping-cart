package cart.dao;

import cart.domain.product.Product;
import cart.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(CartDaoImpl.class)
class CartDaoImplTest {

    private final User user = new User(1L, "hello@naver.com", "myPassword");

    @Autowired
    private CartDaoImpl cartDao;


    @BeforeEach
    void setUp() {
        cartDao.insert(user, 1L);
        cartDao.insert(user, 2L);
    }
    @DisplayName("유저의 카트에 정상적으로 상품을 추가할 수 있다.")
    @Test
    void insert() {
        assertDoesNotThrow(() -> cartDao.insert(user, 1L));
    }

    @DisplayName("유저의 카트에 담긴 상품들을 확인할 수 있다.")
    @Test
    void findAllByUser() {
        // given, when
        final List<Product> products = cartDao.findAllByUser(user);

        // then
        assertAll(
                () -> assertThat(products.get(0).getId()).isEqualTo(1L),
                () -> assertThat(products.get(0).getProductNameValue()).isEqualTo("치킨"),
                () -> assertThat(products.get(1).getId()).isEqualTo(2L),
                () -> assertThat(products.get(1).getProductNameValue()).isEqualTo("초밥")
        );
    }

    @DisplayName("카트에 담긴 상품을 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        final List<Product> products = cartDao.findAllByUser(user);
        final int originalSize = products.size();

        // when
        cartDao.delete(user, 1L);
        final int updatedSize = cartDao.findAllByUser(user).size();

        // then
        assertThat(originalSize - 1).isEqualTo(updatedSize);
    }
}