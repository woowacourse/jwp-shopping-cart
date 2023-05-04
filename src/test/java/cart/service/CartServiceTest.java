package cart.service;

import cart.Pixture;
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
        cartService.putItemIntoCart(1L, AUTH_MEMBER1);
    }

    @DisplayName("장바구니에 없는 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidItem() {
        Assertions.assertThatThrownBy(() -> cartService.putItemIntoCart(3L, AUTH_MEMBER1))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("item을 다시 선택해주세요.");
    }

    @DisplayName("올바르지 않은 사람에 대한 장바구니에 상품을 추가할 수 없다.")
    @Test
    void putItemIntoCart_fail_invalidMember() {
        Assertions.assertThatThrownBy(() -> cartService.putItemIntoCart(1L, AUTH_MEMBER2))
                .isInstanceOf(ServiceIllegalArgumentException.class)
                .hasMessage("email과 password를 다시 입력해주세요.");
    }
}
