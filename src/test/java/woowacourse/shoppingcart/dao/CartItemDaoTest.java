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
    private final Long memberId = 1L;
    private Long bananaId;
    private Long appleId;
    private Long chocolateId;
    private Long cartItemId_1;
    private Long cartItemId_2;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        bananaId = productDao.save(new Product("banana", 1_000, "woowa1.com"));
        appleId = productDao.save(new Product("apple", 2_000, "woowa2.com"));
        chocolateId = productDao.save(new Product("chocolate", 700, "woowa2.com"));

        cartItemId_1 = cartItemDao.save(memberId, bananaId);
        cartItemId_2 = cartItemDao.save(memberId, appleId);
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void save() {
        Long cartItemId = cartItemDao.save(memberId, chocolateId);

        assertThat(cartItemId).isEqualTo(3L);
    }

    @DisplayName("고객 아이디를 넣으면, 해당 고객이 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByMemberId() {
        List<Long> productsIds = cartItemDao.findProductIdsByMemberId(memberId);

        assertThat(productsIds).containsExactly(bananaId, appleId);
    }

    @DisplayName("Member Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByMemberId() {
        List<Long> cartIds = cartItemDao.findIdsByMemberId(memberId);

        assertThat(cartIds).containsExactly(cartItemId_1, cartItemId_2);
    }

    @DisplayName("CartItem Id를 넣으면, 해당 아이템을 장바구니에서 삭제한다.")
    @Test
    void deleteById() {
        cartItemDao.deleteById(cartItemId_1);

        List<Long> productIds = cartItemDao.findProductIdsByMemberId(memberId);

        assertThat(productIds).doesNotContain(cartItemId_1);
    }
}
