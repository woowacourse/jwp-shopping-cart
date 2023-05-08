package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {

    private CartDao cartDao;
    private Long memberId;
    private Long productId;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        MemberDao memberDao = new MemberDao(jdbcTemplate);
        MemberEntity memberEntity = memberDao.findAll().get(0);

        memberId = memberEntity.getId();

        ProductDao productDao = new ProductDao(jdbcTemplate);
        ProductEntity productEntity = productDao.findAll().get(0);

        productId = productEntity.getId();
    }

    @Test
    @DisplayName("add() 메서드를 호출하면 하나의 데이터가 cart에 추가된다")
    void add() {
        int beforeSize = cartDao.findAllByMemberId(memberId).size();
        cartDao.add(memberId, productId);
        int afterSize = cartDao.findAllByMemberId(memberId).size();
        assertThat(afterSize).isEqualTo(beforeSize + 1);
    }

    @Test
    @DisplayName("findCartIdByMemberIdAndProductId() 메서드를 호출하면 해당 product의 id를 반환한다")
    void findCartIdByMemberIdAndProductId() {
        long cartId = cartDao.add(memberId, productId);
        Optional<Long> actual = cartDao.findCartIdByMemberIdAndProductId(
            memberId, productId);

        assertAll(
            () -> assertThat(actual).isPresent(),
            () -> assertThat(actual.get()).isEqualTo(cartId));
    }

    @Test
    @DisplayName("deleteByMemberIdAndProductId() 메서드를 호출하여 제거되었으면 1을 반환한다")
    void deleteByMemberIdAndProductId() {
        cartDao.add(memberId, productId);
        int actual = cartDao.deleteByMemberIdAndProductId(memberId, productId);
        assertThat(actual).isEqualTo(1);
    }
}