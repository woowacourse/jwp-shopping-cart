package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.CartItem;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
public class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    private final String memberEmail = "email1@email.com";

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 담긴 상품을 모두 조회한다.")
    void findAll() {
        int cartId = cartDao.save(2, memberEmail);
        List<CartItem> all = cartDao.findAll(memberEmail);

        assertThat(all.get(0).getProduct().getName()).isEqualTo("치킨");
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void save() {
        int cartId = cartDao.save(3, memberEmail);

        Optional<CartItem> cartItem = cartDao.findById(cartId);
        assertThat(cartItem.get().getProduct().getName()).isEqualTo("햄버거");
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제한다.")
    void deleteById() {
        int cartId = cartDao.save(3, memberEmail);

        cartDao.deleteById(cartId);

        Optional<CartItem> cartItem = cartDao.findById(cartId);
        assertThat(cartItem.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("상품의 id로 cart id 조회한다.")
    void findCartIdByProductId() {
        int cartId = cartDao.save(3, memberEmail);

        List<Integer> cartIdByProductId = cartDao.findCartIdByProductId(3, memberEmail);
        assertThat(cartIdByProductId.get(0)).isEqualTo(cartId);
    }

    @Test
    @DisplayName("Cart id로 상품을 조회한다.")
    void findById() {
        int cartId = cartDao.save(3, memberEmail);
        Optional<CartItem> cartItem = cartDao.findById(cartId);

        assertThat(cartItem.isPresent()).isTrue();
    }

    @AfterEach
    void afterEach() {
        jdbcTemplate.update("TRUNCATE TABLE Cart");
        jdbcTemplate.update("ALTER TABLE Cart ALTER COLUMN id RESTART");
    }
}
