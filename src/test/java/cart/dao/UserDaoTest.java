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
class UserDaoTest {

    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("모든 유저를 불러올 수 있다")
    @Test
    void findAll() {
        //given
        final int count = countRowsInTable(jdbcTemplate, "users");

        //then
        assertThat(userDao.findAll()).hasSize(count);
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
            userDao.addProductToCart(1L, product.getId());

            //then
            assertThat(countRowsInTable(jdbcTemplate, "user_product")).isEqualTo(1);
        }

        @DisplayName("유저 id가 올바르지 않을 때 상품을 추가하면 예외가 발생한다")
        @Test
        void addProductToCart_notExistUser() {
            //given
            final Product product = productDao.save(new Product("상품", "이미지", 1000));

            //then
            assertThatThrownBy(() -> userDao.addProductToCart(0L, product.getId()))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }

        @DisplayName("상품 id가 올바르지 않을 때 카트에 추가하면 예외가 발생한다")
        @Test
        void addProductToCart_notExistCart() {
            assertThatThrownBy(() -> userDao.addProductToCart(1L, 0L))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }

    @DisplayName("장바구니의 상품을 제거할 수 있다")
    @Test
    void deleteProductInCart() {
        //given
        final Product product = productDao.save(new Product("상품", "이미지", 1000));
        final Long userProductId = userDao.addProductToCart(1L, product.getId());

        final int beforeDelete = countRowsInTable(jdbcTemplate, "user_product");

        //when
        userDao.deleteProductInCart(1L, userProductId);

        //then
        assertAll(
                () -> assertThat(beforeDelete).isOne(),
                () -> assertThat(countRowsInTable(jdbcTemplate, "user_product")).isZero());
    }

    @DisplayName("유저의 장바구니 상품 전체를 가져올 수 있다")
    @Test
    void findAllProductsInCart() {
        //given
        final Product product = productDao.save(new Product("상품", "이미지", 1000));

        //when
        userDao.addProductToCart(1L, product.getId());
        userDao.addProductToCart(1L, product.getId());

        //then
        assertThat(userDao.findAllProductsInCart(1L)).hasSize(2);
    }
}
