package cart.dao;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql(value = {"/schema.sql", "/data.sql"})
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    void 데이터_정상_삽입() {
        final var memberId = 1L;
        final var productId = 1L;

        assertThatNoException().isThrownBy(() -> cartDao.insert(memberId, productId));
    }

    @Test
    void 존재하지_않는_외래키를_참조하면_예외가_발생한다() {
        final var memberId = 10L;
        final var productId = 10L;

        assertThatThrownBy(() -> cartDao.insert(memberId, productId))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void 멤버ID로_저장된_상품들을_모두_가져온다() {
        final long memberId = 1L;

        cartDao.insert(memberId, 1L);
        cartDao.insert(memberId, 2L);

        assertThat(cartDao.findByMemberId(memberId).size()).isEqualTo(2);
    }
}
