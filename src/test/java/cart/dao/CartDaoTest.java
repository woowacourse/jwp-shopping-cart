package cart.dao;

import cart.dao.exception.ProductNotFoundException;
import cart.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class CartDaoTest {

    private final CartDao cartDao;
    private int key;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CartDaoTest(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        final var simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
        final var parameterSource = new BeanPropertySqlParameterSource(
                Product.createWithoutId("product1", 100, "url.com")
        );
        final KeyHolder keyHolder =
                simpleJdbcInsert.executeAndReturnKeyHolder(parameterSource);
        key = keyHolder.getKey().intValue();

        jdbcTemplate.update("INSERT INTO cart (user_id, product_id) " +
                "VALUES (1, ?), (1, ?)", this.key, this.key);

        jdbcTemplate.update("INSERT INTO cart (user_id, product_id) " +
                "VALUES (2, ?)", this.key);
    }

    @DisplayName("1번 유저의 장바구니를 모두 조회한다.")
    @Test
    void findAllOfProductForMember1Test() {
        final List<Product> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(2);
    }

    @DisplayName("1번 유저의 장바구니에 상품을 넣는다.")
    @Test
    void addProductToCartTest() {
        cartDao.addProduct(1, key);
        cartDao.addProduct(1, key);
        cartDao.addProduct(1, key);

        final List<Product> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(5);
    }

    @DisplayName("1번 유저의 장바구니 1번 상품을 지운다.")
    @Test
    void deleteProductFromCart() {
        cartDao.deleteProduct(1, key);

        final List<Product> products = cartDao.findByUserId(1);

        assertThat(products).hasSize(1);
    }

    @DisplayName("삭제할 product가 없을 시 예외가 발생한다")
    @Test
    void deleteProductException() {
        cartDao.deleteProduct(1, key);
        cartDao.deleteProduct(1, key);
        assertThatThrownBy(() -> cartDao.deleteProduct(1, key))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
