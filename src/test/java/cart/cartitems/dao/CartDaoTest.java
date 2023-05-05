package cart.cartitems.dao;

import cart.cartitems.dto.CartItemDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
// DDL
@Sql(value = "/schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Import(CartItemTestConfig.class)
@ComponentScan(basePackages = {"cart.product.dao", "cart.member.dao", "cart.cartitems.dao"})
class CartDaoTest {

    private static final int FIRST_MEMBER_ID = 1;
    private static final int SECOND_MEMBER_ID = 2;
    private static final int FIRST_PRODUCT_ID = 1;
    private static final int SECOND_PRODUCT_ID = 2;
    private static final int THIRD_PRODUCT_ID = 3;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private CartItemTestConfig cartItemTestConfig;

    /**
     * beforeEach를 실행하면 MEMBER는 2명 Product는 3개가 들어간 상태로 초기화 된다.
     */

    @BeforeEach
    void beforeEach() {
        cartItemTestConfig.setMembersAndProducts();
    }

    @Test
    @DisplayName("유저가 담은 모든 상품의 아이디를 가져온다")
    void findProductIdsByMemberId() {
        cartDao.saveItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        cartDao.saveItem(new CartItemDto(FIRST_MEMBER_ID, SECOND_PRODUCT_ID));
        cartDao.saveItem(new CartItemDto(SECOND_MEMBER_ID, THIRD_PRODUCT_ID));

        final List<Long> itemsIds = cartDao.findProductIdsByMemberId(1);

        assertThat(itemsIds).hasSize(2);
    }

    @Test
    @DisplayName("containsItem은 이미 상품이 존재하면 true를 반환한다")
    void containsItem() {
        cartDao.saveItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));

        assertThat(cartDao.containsItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID))).isTrue();
    }

    @Test
    @DisplayName("containsItem은 상품이 존재하지 않으면 false를 반환한다")
    void notContainsItem() {
        assertThat(cartDao.containsItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID))).isFalse();
    }


    @Test
    @DisplayName("delete는 제품을 삭제하고 삭제된 행의 개수를 반환한다")
    void delete() {
        cartDao.saveItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        cartDao.saveItem(new CartItemDto(FIRST_MEMBER_ID, SECOND_PRODUCT_ID));

        final int oneRowDeleted = cartDao.deleteItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));
        final int noRowDeleted = cartDao.deleteItem(new CartItemDto(FIRST_MEMBER_ID, FIRST_PRODUCT_ID));

        assertAll(
                () -> assertThat(oneRowDeleted).isEqualTo(1),
                () -> assertThat(cartDao.findProductIdsByMemberId(FIRST_MEMBER_ID)).hasSize(1),
                () -> assertThat(noRowDeleted).isEqualTo(0)
        );
    }
}
