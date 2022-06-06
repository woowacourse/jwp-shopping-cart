package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.auth.dao.MemberDao;
import woowacourse.auth.domain.Member;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class CartItemDaoTest {

    private final CartItemDao cartItemDao;
    private final MemberDao memberDao;

    public CartItemDaoTest(JdbcTemplate jdbcTemplate) {
        cartItemDao = new CartItemDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        Member member = new Member("abc@woowacourse.com", "1q2w3e4r!", "닉네임");
        memberDao.save(member);
    }

    @DisplayName("장바구니에 상품을 추가한다.")
    @Test
    void addCartItem() {
        assertThatCode(() -> cartItemDao.addCartItem(1L, 1L, 5))
                .doesNotThrowAnyException();
    }

    @DisplayName("이미 회원의 장바구니에 담긴 상품인지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1, 1, true", "2, 1, false", "1, 2, false", "2, 2, false"})
    void isExistsMemberIdAndProductId(long memberId, long productId, boolean expected) {
        cartItemDao.addCartItem(1L, 1L, 5);

        boolean actual = cartItemDao.isExistsMemberIdAndProductId(memberId, productId);

        assertThat(actual).isEqualTo(expected);
    }
}
