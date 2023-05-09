package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import cart.dao.CartDao;
import cart.dao.CartRepository;
import cart.dao.MemberDao;
import cart.dao.MySQLCartDao;
import cart.dao.MySQLMemberDao;
import cart.dao.MySQLProductDao;
import cart.dao.ProductDao;
import cart.domain.Member;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql", "/cart_data.sql"})
@JdbcTest
class CartServiceTest {
    private static final String MEMBER_NOT_FOUND_MESSAGE = "존재하지 않는 회원 정보입니다.";
    private static final Member notExistMember=new Member("songsy405@naver.com", "1234");

    private CartService cartService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        CartDao mySQLCartDao = new MySQLCartDao(jdbcTemplate);
        MemberDao mySQLMemberDao = new MySQLMemberDao(jdbcTemplate);
        ProductDao mySQLProductDao = new MySQLProductDao(jdbcTemplate);
        CartRepository cartRepository = new CartRepository(mySQLCartDao, mySQLProductDao);
        cartService=new CartService(cartRepository, mySQLMemberDao);
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해 add() 메서드를 호출하면 예외가 발생한다.")
    void loadCartProducts_fail_because_not_exist_member() {
        assertThatThrownBy(() -> cartService.loadCartProducts(notExistMember)).isInstanceOf(
            NoSuchElementException.class).hasMessage(MEMBER_NOT_FOUND_MESSAGE);
    }

    @Test
    @DisplayName("존재하지 않는 회원에 대해 add() 메서드를 호출하면 예외가 발생한다.")
    void addCart_fail_because_not_exist_member() {
        assertThatThrownBy(() -> cartService.addCart(1L, notExistMember)).isInstanceOf(
            NoSuchElementException.class).hasMessage(MEMBER_NOT_FOUND_MESSAGE);
    }
    @Test
    @DisplayName("존재하지 않는 회원에 대해 remove() 메서드를 호출하면 예외가 발생한다.")
    void removeCart_fail_because_not_exist_member() {
        assertThatThrownBy(() -> cartService.removeCart(1L, notExistMember)).isInstanceOf(
            NoSuchElementException.class).hasMessage(MEMBER_NOT_FOUND_MESSAGE);
    }
}