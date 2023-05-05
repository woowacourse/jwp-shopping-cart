package cart.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.entity.CartEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@JdbcTest
public class JdbcCartDaoTest {

    private static final long KOKODAK_ID = 1L;
    private static final long PIZZA_ID = 1L;
    private static final long CHICKEN_ID = 2L;
    private static final int KOKODAK_PIZZA_INDEX = 0;
    private static final int KOKODAK_CHICKEN_INDEX = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new JdbcCartDao(jdbcTemplate);
    }

    @Test
    void 상품을_장바구니에_추가하고_사용자의_장바구니를_조회하여_찾는다() {
        final CartEntity kokodakPizzaCartEntity = new CartEntity(KOKODAK_ID, PIZZA_ID);
        final CartEntity kokodakChickenCartEntity = new CartEntity(KOKODAK_ID, CHICKEN_ID);

        cartDao.save(kokodakPizzaCartEntity);
        cartDao.save(kokodakChickenCartEntity);

        final List<CartEntity> cartEntities = cartDao.findByMemberId(KOKODAK_ID);

        final SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(cartEntities.size()).isEqualTo(2);

        softAssertions.assertThat(cartEntities.get(KOKODAK_PIZZA_INDEX).getMemberId()).isEqualTo(KOKODAK_ID);
        softAssertions.assertThat(cartEntities.get(KOKODAK_PIZZA_INDEX).getProductId()).isEqualTo(PIZZA_ID);

        softAssertions.assertThat(cartEntities.get(KOKODAK_CHICKEN_INDEX).getMemberId()).isEqualTo(KOKODAK_ID);
        softAssertions.assertThat(cartEntities.get(KOKODAK_CHICKEN_INDEX).getProductId()).isEqualTo(CHICKEN_ID);

        softAssertions.assertAll();
    }

    @Test
    void 사용자의_장바구니에_담긴_상품을_삭제한다() {
        final CartEntity kokodakPizzaCartEntity = new CartEntity(KOKODAK_ID, PIZZA_ID);
        cartDao.save(kokodakPizzaCartEntity);

        cartDao.delete(KOKODAK_ID, PIZZA_ID);

        final List<CartEntity> cartEntities = cartDao.findByMemberId(KOKODAK_ID);
        assertThat(cartEntities.isEmpty()).isTrue();
    }
}
