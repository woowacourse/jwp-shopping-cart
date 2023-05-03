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
@Sql("/cart_initialize.sql")
class CartDaoTest {

    private CartDao cartDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private long customerId = 1L;
    private long productId = 1L;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @DisplayName("사용자 id와 상품 id로 장바구니를 담는다.")
    @Test
    void insert() {
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
    void findAllCartProductByCustomerId() {
        // given
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

    @DisplayName("사용자 장바구니 상품을 삭제할 수 있다.")
    @Test
    void deleteById() {
        // given
        long cartId = cartDao.insert(customerId, productId);

        // when
        cartDao.deleteById(cartId);

        // then
        List<CartProductDto> cartProducts = cartDao.findAllCartProductByCustomerId(customerId);
        assertThat(cartProducts).isEmpty();
    }

    @DisplayName("상품이 장바구니에 저장되어 있으면 true가 반환된다.")
    @Test
    void trueIfProductIdInCustomerCart() {
        // given
        cartDao.insert(customerId, productId);

        // when
        boolean isProductInCart = cartDao.isProductIdInCustomerCart(customerId, productId);

        // then
        assertThat(isProductInCart).isTrue();
    }

    @DisplayName("상품이 장바구니에 저장되어 있으면 false가 반환된다.")
    @Test
    void falseIfProductIdNotInCustomerCart() {
        // given

        // when
        boolean isProductInCart = cartDao.isProductIdInCustomerCart(customerId, productId);

        // then
        assertThat(isProductInCart).isFalse();
    }
}