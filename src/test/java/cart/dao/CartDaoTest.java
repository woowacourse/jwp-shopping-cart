package cart.dao;

import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql({"/data-test.sql"})
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        ProductDao productDao = new ProductDao(jdbcTemplate);

        Product product = Product.from(1L, "치킨", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg", 1000);
        productDao.save(product);
    }

    @DisplayName("장바구니에 멤버가 담은 상품이 정상적으로 저장된다.")
    @Test
    void save() {
        // when, then
        assertDoesNotThrow(() -> cartDao.save(1L, 1L));
    }

    @DisplayName("장바구니에 memberId와 productId에 해당하는 상품이 존재하면 cartId를 반환한다.")
    @Test
    void existsByMemberIdAndProductId() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        cartDao.save(memberId, productId);

        // when
        Optional<Long> cartId = cartDao.existsByMemberIdAndProductId(memberId, productId);

        // then
        assertThat(cartId.isPresent()).isTrue();
        assertThat(cartId.get()).isEqualTo(1L);
    }

    @DisplayName("사용자 이메일로 장바구니에 담은 상품들을 찾아 반환한다.")
    @Test
    void findByEmail() {
        // given
        cartDao.save(1L, 1L);

        // when
        List<Product> products = cartDao.findByEmail("a@a.com");

        // then
        assertSoftly(softly -> {
            softly.assertThat(products.size()).isEqualTo(1);
            softly.assertThat(products.get(0)).hasFieldOrPropertyWithValue("id", 1L)
                    .hasFieldOrPropertyWithValue("name", "치킨")
                    .hasFieldOrPropertyWithValue("imageUrl", "https://pelicana.co.kr/resources/images/menu/best_menu02_200824.jpg")
                    .hasFieldOrPropertyWithValue("price", 1000);
        });
    }

    @DisplayName("장바구니에 있는 ProductId에 해당하는 상품을 삭제한다.")
    @Test
    void deleteByProductId() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        cartDao.save(memberId, productId);

        // when, then
        assertDoesNotThrow(() -> cartDao.deleteByProductId(productId));
    }

    @DisplayName("ProductId와 MemberId로 장바구니에 있는 상품을 삭제한다.")
    @Test
    void deleteByMemberIdAndProductId() {
        // given
        Long memberId = 1L;
        Long productId = 1L;
        cartDao.save(memberId, productId);

        // when, then
        assertSoftly(softly -> {
            assertDoesNotThrow(() -> cartDao.deleteByMemberIdAndProductId(memberId, productId));
            softly.assertThat(cartDao.existsByMemberIdAndProductId(memberId, productId).isPresent()).isFalse();
        });
    }
}
