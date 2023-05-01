package cart.cartitems.dao;

import cart.cartitems.dto.CartItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
// DDL
@Sql(value = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CartItemDaoTest {

    private static final int FIRST_MEMBER_ID = 1;
    private static final int SECOND_MEMBER_ID = 2;
    private static final int FIRST_PRODUCT_ID = 1;
    private static final int SECOND_PRODUCT_ID = 2;
    private static final int THIRD_PRODUCT_ID = 3;

    private final CartItemDao cartItemDao;
    private final CarItemDaoTestConfig carItemDaoTestConfig;

    @Autowired
    CartItemDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.cartItemDao = new CartItemDao(namedParameterJdbcTemplate);
        this.carItemDaoTestConfig = new CarItemDaoTestConfig(namedParameterJdbcTemplate);
    }

    /**
     * beforeEach를 실행하면 MEMBER는 2명 Product는 3개가 들어간 상태로 초기화 된다.
     */

    @BeforeEach
    void beforeEach() {
        carItemDaoTestConfig.beforeEach();
    }

    @Test
    @DisplayName("유저가 담은 모든 상품의 아이디를 가져온다")
    void findProductIdsByMemberId() {
        cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, SECOND_PRODUCT_ID));
        cartItemDao.saveItemOfMember(new CartItemDto(SECOND_MEMBER_ID, THIRD_PRODUCT_ID));

        final List<Long> itemsIds = cartItemDao.findProductIdsByMemberId(1);

        assertThat(itemsIds).hasSize(2);
    }

    @Test
    @DisplayName("save는 중복된 상품을 담으면 예외를 발생시킨다.")
    void saveException() {
        cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));

        assertThatThrownBy(() -> cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID)))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    @DisplayName("delete는 제품을 삭제하고 삭제된 행의 개수를 반환한다")
    void delete() {
        cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        cartItemDao.saveItemOfMember(new CartItemDto(FIRST_MEMBER_ID, SECOND_PRODUCT_ID));

        final int oneRowDeleted = cartItemDao.deleteItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        final int noRowDeleted = cartItemDao.deleteItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));

        assertAll(
                () -> assertThat(oneRowDeleted).isEqualTo(1),
                () -> assertThat(cartItemDao.findProductIdsByMemberId(FIRST_MEMBER_ID)).hasSize(1),
                () -> assertThat(noRowDeleted).isEqualTo(0)
        );
    }
}
