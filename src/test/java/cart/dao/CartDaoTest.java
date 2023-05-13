package cart.dao;

import cart.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
        cartDao = new CartDao(jdbcTemplate);
    }

    @Nested
    @DisplayName("장바구니 상품 추가 테스트")
    class AddProductToCart {
        @DisplayName("유저의 장바구니에 상품을 추가할 수 있다")
        @Test
        void addProductToCart() {
            //given
            final Product product = productDao.save(new Product("상품", "이미지", 1000));

            //when
            cartDao.addProductToCart(1L, product.getId());

            //then
            assertThat(countRowsInTable(jdbcTemplate, "cart")).isEqualTo(1);
        }

        @DisplayName("유저 id가 올바르지 않을 때 상품을 추가하면 예외가 발생한다")
        @Test
        void addProductToCart_notExistUser() {
            //given
            final Product product = productDao.save(new Product("상품", "이미지", 1000));

            //then
            assertThatThrownBy(() -> cartDao.addProductToCart(0L, product.getId()))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        @DisplayName("상품 id가 올바르지 않을 때 카트에 추가하면 예외가 발생한다")
        @Test
        void addProductToCart_notExistCart() {
            assertThatThrownBy(() -> cartDao.addProductToCart(1L, 0L))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @DisplayName("장바구니의 상품을 제거할 수 있다")
    @Test
    void deleteProductInCart() {
        //given
        final Product product = productDao.save(new Product("상품", "이미지", 1000));
        final Long userProductId = cartDao.addProductToCart(1L, product.getId());

        final int beforeDelete = countRowsInTable(jdbcTemplate, "cart");

        //when
        cartDao.deleteProductInCart(1L, userProductId);

        //then
        assertAll(
                () -> assertThat(beforeDelete).isOne(),
                () -> assertThat(countRowsInTable(jdbcTemplate, "cart")).isZero());
    }

    @DisplayName("유저의 장바구니 상품 전체를 가져올 수 있다")
    @Test
    void findAllProductsInCart() {
        //given
        final Product product = productDao.save(new Product("상품", "이미지", 1000));

        //when
        cartDao.addProductToCart(1L, product.getId());
        cartDao.addProductToCart(1L, product.getId());

        //then
        assertThat(cartDao.findCartItemsByUserId(1L)).hasSize(2);
    }
}
