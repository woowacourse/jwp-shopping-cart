package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.dao.MemberDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("classpath:schema.sql")
public class CartItemDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartItemDao cartItemDao;
    private ProductDao productDao;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        cartItemDao = new CartItemDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);

        memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        productDao.save(createProduct("banana", 1_000, "woowa1.com"));
        productDao.save(createProduct("apple", 2_000, "woowa2.com"));
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {
        final Long cartId = cartItemDao.addCartItem(1L, 1L);
        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("멤버 아이디를 넣으면, 해당 장바구니 아이디들을 가져온다.")
    @Test
    void findIdsByMemberId() {
        cartItemDao.addCartItem(1L, 1L);

        final List<Long> cartIds = cartItemDao.findIdsByMemberId(1L);
        assertThat(cartIds).containsExactly(1L);
    }
}
