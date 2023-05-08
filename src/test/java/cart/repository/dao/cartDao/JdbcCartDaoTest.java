package cart.repository.dao.cartDao;

import cart.dto.CartItemDto;
import cart.entity.Cart;
import cart.entity.Product;
import cart.repository.dao.productDao.JdbcProductDao;
import cart.repository.dao.productDao.ProductDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcCartDaoTest {

    @Autowired
    private DataSource dataSource;

    private JdbcCartDao jdbcCartDao;

    @BeforeEach
    void setUp() {
        jdbcCartDao = new JdbcCartDao(dataSource);
    }

    @Test
    void 장바구니에_저장한다() {
        Long memberId = 1L;
        Long productId = 10L;
        Cart cart = new Cart(memberId, productId);

        Long id = jdbcCartDao.save(cart);

        assertThat(id).isPositive();
    }

    @Test
    void 회원ID로_장바구니를_가져온다() {
        Long memberId = 1L;
        Product product = new Product("과자", "http://www.naver.com/dsad.png", 1000);
        Long productId = new JdbcProductDao(dataSource).save(product);

        Cart cart = new Cart(memberId, productId);
        Long cartId = jdbcCartDao.save(cart);

        List<CartItemDto> products = jdbcCartDao.findAllCartItemsByMemberId(memberId);

        assertThat(products.get(0).getCartId()).isEqualTo(cartId);
    }

    @Test
    void 장바구니ID로_장바구니_상품을_삭제한다() {
        Long memberId = 2L;
        Long productId = 3L;
        Cart cart = new Cart(memberId, productId);
        Long cartId = jdbcCartDao.save(cart);


        int amountOfCartDeleted = jdbcCartDao.deleteByCartId(cartId);

        assertThat(amountOfCartDeleted).isEqualTo(1);
    }
}
