package cart.dao;

import cart.entity.Cart;
import cart.entity.Product;
import cart.entity.vo.Email;
import cart.exception.TableIdNotFoundException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class JdbcCartDaoTest {

    private static final RowMapper<Product> productRowMapper = (rs, rowNum) ->
            new Product(rs.getLong("id"),
                    rs.getString("product_name"),
                    rs.getInt("product_price"),
                    rs.getString("product_image"));

    private static final Email testUserEmail = new Email("test@email.com");

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final JdbcCartDao cartDao;

    @Autowired
    public JdbcCartDaoTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("cart_added_product")
                .usingGeneratedKeyColumns("id");
        this.cartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    @DisplayName("카트 추가 테스트")
    void add_to_cart_test() {
        // given
        final Product product = findFirstProduct();

        // when
        final long insertedCartId = cartDao.insert(testUserEmail, product.getId());

        // then
        final Map<String, Object> result = jdbcTemplate.queryForMap("SELECT * FROM cart_added_product WHERE id = ?", insertedCartId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.get("user_email")).isEqualTo(testUserEmail.getValue());
            softly.assertThat(result.get("product_id")).isEqualTo(product.getId());
        });
    }

    private Product findFirstProduct() {
        return jdbcTemplate.queryForObject("SELECT * FROM  products LIMIT 1;", productRowMapper);
    }

    @Test
    @DisplayName("id로 카트 아이템 검색 테스트")
    void find_by_id_test() {
        // given
        final Product product = findFirstProduct();
        final long cartId = insertToCartTable(testUserEmail.getValue(), product.getId());

        // when
        final Cart cart = cartDao.findById(cartId);
        final long addedProductId = cart.getProductId();

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(cart.getUserEmail()).isEqualTo(testUserEmail);
            softly.assertThat(addedProductId).isEqualTo(product.getId());
        });
    }

    @Test
    @DisplayName("id로 검색 실패 테스트")
    void findById_fail_test() {
        // given
        final long id = findLastInsertedId() + 1L;

        // when & then
        assertThatThrownBy(() -> cartDao.findById(id))
                .isInstanceOf(TableIdNotFoundException.class)
                .hasMessage("해당 카트 id를 찾을 수 없습니다. 입력된 카트 id : " + id);
    }

    private long findLastInsertedId() {
        return jdbcTemplate.queryForObject("SELECT max(id) FROM products", Long.class);
    }

    private long insertToCartTable(final String email, final long productId) {
        MapSqlParameterSource source = new MapSqlParameterSource()
                .addValue("user_email", email)
                .addValue("product_id", productId);

        return simpleJdbcInsert.executeAndReturnKey(source).longValue();
    }

    @Test
    @DisplayName("사용자로 카트 상품 조회 테스트")
    void findby_user_email_test() {
        // given
        final Product product = findFirstProduct();
        final int cartAddedCount = 3;
        for (int i = 0; i < cartAddedCount; i++) {
            insertToCartTable(testUserEmail.getValue(), product.getId());
        }

        // when
        final Map<Cart, Product> productsByUserEmail = cartDao.findProductsByUserEmail(testUserEmail);

        // then
        assertThat(productsByUserEmail.entrySet()).hasSize(cartAddedCount);
    }

    @Test
    @DisplayName("카트에서 제거 테스트")
    void delete_test() {
        // given
        final Product product = findFirstProduct();
        final long insertedId = insertToCartTable(testUserEmail.getValue(), product.getId());

        // when
        cartDao.delete(insertedId);

        // then
        assertThatThrownBy(() -> jdbcTemplate.queryForObject("SELECT id FROM cart_added_product where id = ?", Long.class, insertedId))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
