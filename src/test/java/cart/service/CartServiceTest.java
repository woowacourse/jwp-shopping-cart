package cart.service;

import cart.authorization.AuthorizationInformation;
import cart.dao.JdbcItemDao;
import cart.dao.JdbcMemberDao;
import cart.dto.ItemResponse;
import cart.exception.ServiceIllegalArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.Pixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private JdbcItemDao jdbcItemDao;

    @Autowired
    private JdbcMemberDao jdbcMemberDao;

    @BeforeEach
    void setUp() {
        jdbcItemDao.save(CREATE_ITEM1);
        jdbcItemDao.save(CREATE_ITEM2);

        jdbcMemberDao.save(AUTH_MEMBER1);

        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());
        cartService.putItemIntoCart(1L, authorizationInformation);
    }


    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void putItemIntoCart_success() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());
        cartService.putItemIntoCart(2L, authorizationInformation);
    }

    @DisplayName("장바구니에 없는 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidItem() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());

        assertThatThrownBy(() -> cartService.putItemIntoCart(3L, authorizationInformation))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("item을 다시 선택해주세요.");
    }

    @DisplayName("올바르지 않은 사람에 대한 장바구니에 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidMember() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER2.getEmail(), AUTH_MEMBER2.getPassword());

        assertThatThrownBy(() -> cartService.putItemIntoCart(1L, authorizationInformation))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("email과 password를 다시 입력해주세요.");
    }

    @DisplayName("장바구니에 있는 상품을 조회할 수 있다.")
    @Test
    void findAllItemByAuthInfo_success() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());

        List<ItemResponse> itemResponses = cartService.findAllItemByAuthInfo(authorizationInformation);

        ItemResponse expected = new ItemResponse(ITEM1.getId(), ITEM1.getName(), ITEM1.getImageUrl(), ITEM1.getPrice());
        assertAll(
                () -> assertThat(itemResponses).hasSize(1),
                () -> assertThat(itemResponses.get(0))
                        .usingRecursiveComparison()
                        .isEqualTo(expected)
        );
    }

    @DisplayName("장바구니에 있는 상품을 삭제할 수 있다.")
    @Test
    void deleteItemByItemId_success() {
        cartService.deleteItemByItemId(1L);

        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());

        List<ItemResponse> itemResponses = cartService.findAllItemByAuthInfo(authorizationInformation);

        assertThat(itemResponses).hasSize(0);
    }

    @DisplayName("장바구니에 없는 상품을 삭제하면 예외가 발생한다.")
    @Test
    void deleteItemByItemId_fail() {

        assertThatThrownBy(() -> cartService.deleteItemByItemId(2L))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("상품 정보를 다시 입력해주세요.");
    }
}
