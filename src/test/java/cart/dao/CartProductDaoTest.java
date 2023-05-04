package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.CartProduct;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartProductDaoTest {

    private static final String PRODUCT_INSERT_SQL = "insert into product(id, name, price, image_url) values (?,?,?,?)";
    private static final String MEMBER_INSERT_SQL = "insert into member(id, email, password) values (?, ?,?)";
    private static final long PRODUCT_ID = 1L;
    private static final long MEMBER_ID = 1L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartProductDao cartProductDao;

    @BeforeEach
    void setUp() {
        cartProductDao = new CartProductDao(jdbcTemplate);
    }

    @DisplayName("cartProduct를 정상적으로 저장한다.")
    @Test
    void save_success() {
        //given
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);
        //when
        Long actual = cartProductDao.save(MEMBER_ID, PRODUCT_ID);
        //then
        assertThat(actual).isPositive();
    }

    private void saveProductAndMember(Long memberId, Long productId) {
        jdbcTemplate.update(MEMBER_INSERT_SQL, memberId, "email@naver.com", "password");
        jdbcTemplate.update(PRODUCT_INSERT_SQL, productId, "name", 1000, "url");
    }

    @DisplayName("특정한 멤버의 모든 CartProduct를 반환한다.")
    @Test
    void find_cart_products_by_member_id() {
        //given
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);
        cartProductDao.save(MEMBER_ID, PRODUCT_ID);
        //when
        List<CartProduct> actual = cartProductDao.findAllByMemberId(MEMBER_ID);
        //then
        assertThat(actual.size()).isEqualTo(1);
    }

    @DisplayName("CartProduct를 삭제한다.")
    @Test
    void delete_cart_product() {
        //given
        saveProductAndMember(MEMBER_ID, PRODUCT_ID);
        Long givenCartProductId = cartProductDao.save(MEMBER_ID, PRODUCT_ID);
        //when
        int actual = cartProductDao.deleteById(givenCartProductId);
        //then
        assertThat(actual).isEqualTo(1);
    }
}
