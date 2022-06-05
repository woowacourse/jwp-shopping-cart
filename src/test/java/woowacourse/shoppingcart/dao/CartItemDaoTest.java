package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final JdbcTemplate jdbcTemplate;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        productDao.save(new Product("banana", 1_000, "woowa1.com"));
        productDao.save(new Product("apple", 2_000, "woowa2.com"));

        jdbcTemplate.update("INSERT INTO cart_item(member_id, product_id) VALUES(?, ?)", 1L, 1L);
        jdbcTemplate.update("INSERT INTO cart_item(member_id, product_id) VALUES(?, ?)", 1L, 2L);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long memberId = 1L;
        final Long productId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(memberId, productId);

        // then
        assertThat(cartId).isEqualTo(3L);
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByMemberId() {

        // given
        final Long memberId = 1L;

        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByMemberId(memberId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Member Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByMemberId() {

        // given
        final Long memberId = 1L;

        // when
        final List<Long> cartIds = cartItemDao.findIdsByMemberId(memberId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("Member Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void deleteCartItem() {

        // given
        final Long cartId = 1L;

        // when
        cartItemDao.deleteCartItem(cartId);

        // then
        final Long memberId = 1L;
        final List<Long> productIds = cartItemDao.findProductIdsByMemberId(memberId);

        assertThat(productIds).containsExactly(2L);
    }
}
