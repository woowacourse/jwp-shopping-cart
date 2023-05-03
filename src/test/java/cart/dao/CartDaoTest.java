package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.dto.CartProductDto;
import cart.dao.entity.CartEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
class CartDaoTest {

    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @Sql("/cart_initialize.sql")
    @DisplayName("사용자 id와 상품 id로 장바구니를 담는다.")
    @Test
    void insert() {
        // given
        long customerId = 1L;
        long productId = 1L;

        // when
        long savedId = cartDao.insert(customerId, productId);

        // then
        String sql = "SELECT * FROM cart";
        List<CartEntity> carts = jdbcTemplate.query(sql, (rs, rowNum) ->
                new CartEntity.Builder()
                        .id(rs.getLong("id"))
                        .customerId(rs.getLong("customer_id"))
                        .productId(rs.getLong("product_id"))
                        .build());

        assertThat(carts.get(0))
                .usingRecursiveComparison()
                .isEqualTo(new CartEntity.Builder()
                        .id(savedId)
                        .productId(productId)
                        .customerId(customerId));
    }

    @DisplayName("사용자의 장바구니를 모두 조회할 수 있다.")
    @Test
    @Sql("/cart_initialize.sql")
    void findAllCartProductByCustomerId() {
        // given
        long customerId = 1L;
        long productId = 1L;
        long cartId = cartDao.insert(customerId, productId);
        String name = "baron";
        String imgUrl = "tempUrl";
        int price = 2000;

        // when
        List<CartProductDto> cartProducts = cartDao.findAllCartProductByCustomerId(customerId);

        // then
        List<CartProductDto> expectedCartProducts = List.of(new CartProductDto(cartId, name, price, imgUrl));
        assertThat(cartProducts).usingRecursiveComparison()
                .isEqualTo(expectedCartProducts);
    }
}