package cart.service;

import cart.Pixture;
import cart.authorization.AuthorizationInformation;
import cart.dao.JdbcItemDao;
import cart.dao.JdbcMemberDao;
import cart.exception.ServiceIllegalArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static cart.Pixture.*;

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
    }


    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void putItemIntoCart_success() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());
        cartService.putItemIntoCart(1L, authorizationInformation);
    }

    @DisplayName("장바구니에 없는 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidItem() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER1.getEmail(), AUTH_MEMBER1.getPassword());

        Assertions.assertThatThrownBy(() -> cartService.putItemIntoCart(3L, authorizationInformation))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("item을 다시 선택해주세요.");
    }

    @DisplayName("올바르지 않은 사람에 대한 장바구니에 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidMember() {
        AuthorizationInformation authorizationInformation = new AuthorizationInformation(AUTH_MEMBER2.getEmail(), AUTH_MEMBER2.getPassword());

        Assertions.assertThatThrownBy(() -> cartService.putItemIntoCart(1L, authorizationInformation))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("email과 password를 다시 입력해주세요.");
    }
}
