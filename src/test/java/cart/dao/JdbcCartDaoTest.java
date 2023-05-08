package cart.dao;

import cart.entity.Cart;
import cart.entity.PutCart;
import cart.exception.DaoDuplicateException;
import cart.exception.ServiceIllegalArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static cart.Pixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql({"classpath:test_init.sql"})
class JdbcCartDaoTest {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        itemDao.save(CREATE_ITEM1);
        itemDao.save(CREATE_ITEM2);

        memberDao.save(AUTH_MEMBER1);
        memberDao.save(AUTH_MEMBER2);

        cartDao.save(PUT_CART1);
        cartDao.save(PUT_CART2);
    }

    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void save_success() {
        cartDao.save(new PutCart(2L, 1L));
    }

    @DisplayName("장바구니에 이미 상품을 담은 경우 상품을 추가할 수 없다.")
    @Test
    void save_fail() {
        Assertions.assertThatThrownBy(() -> cartDao.save(PUT_CART1))
                .isInstanceOf(DaoDuplicateException.class)
                .hasMessage("이미 장바구니에 담은 상품입니다.");
    }

    @DisplayName("사용자 id를 기준으로 장바구니에 저장된 값을 조회할 수 있다.")
    @Test
    void findAllByMemberId_success() {
        List<Cart> carts = cartDao.findAllByMemberId(1L);

        assertAll(
                () -> assertThat(carts).hasSize(2),
                () -> assertThat(carts.get(0))
                        .usingRecursiveComparison()
                        .isEqualTo(CART1),
                () -> assertThat(carts.get(1))
                        .usingRecursiveComparison()
                        .isEqualTo(CART2)
        );
    }

    @DisplayName("cart에 이미 상품이 담겨져있다면 true를 반환한다.")
    @Test
    void findAllByMemberId_true() {
        assertThat(cartDao.isCartExists(PUT_CART1)).isTrue();
    }

    @DisplayName("cart에 상품이 담겨져있지않다면 false를 반환한다.")
    @Test
    void findAllByMemberId_false() {
        assertThat(cartDao.isCartExists(PUT_CART3)).isFalse();
    }

    @DisplayName("카드에 있는 상품 삭제 할 수 있다.")
    @Test
    void delete_success() {
        int deleteRow = cartDao.delete(PUT_CART1);

        List<Cart> carts = cartDao.findAllByMemberId(1L);

        assertAll(
                () -> assertThat(deleteRow).isEqualTo(1),
                () -> assertThat(carts).hasSize(1)
        );
    }

    @DisplayName("카드에 없는 상품 삭제하면 0이 반환된다.")
    @Test
    void delete_fail() {

        assertThat(cartDao.delete(PUT_CART3)).isZero();
    }
}
