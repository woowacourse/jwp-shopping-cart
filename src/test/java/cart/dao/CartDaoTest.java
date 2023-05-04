package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.dao.entity.CartEntity;
import cart.dao.entity.ItemEntity;
import cart.domain.Item;
import cart.domain.Member;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartDao cartDao;
    MemberDao memberDao;
    ItemDao itemDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        itemDao = new ItemDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원 ID와 상품 ID에 해당하는 값을 추가한다.")
    void insertSuccess() {
        Long memberId = memberDao.insert(new Member("asdfafs@naver.com", "testPW", "test"));
        Long itemId = itemDao.insert(new Item("note", "image@com", 10000));

        Long cartId = cartDao.insert(memberId, itemId);

        assertThat(cartId).isPositive();
    }

    @Test
    @DisplayName("장바구니에서 회원 ID와 상품 ID에 해당하는 상품을 가져온다.")
    void findByMemberAndItemSuccess() {
        Long memberId = memberDao.insert(new Member("asdfafs@naver.com", "testPW", "test"));
        Long itemId = itemDao.insert(new Item("note", "image@com", 10000));
        Long cartId = cartDao.insert(memberId, itemId);

        CartEntity cartEntity = cartDao.findByMemberIdAndItemId(memberId, itemId).get();

        assertAll(
                () -> assertThat(cartEntity.getId()).isEqualTo(cartId),
                () -> assertThat(cartEntity.getMemberId()).isEqualTo(memberId),
                () -> assertThat(cartEntity.getItemId()).isEqualTo(itemId)
        );
    }

    @Test
    @DisplayName("장바구니에서 회원 ID에 해당하는 상품을 모두 가져온다.")
    void findAllByMemberSuccess() {
        Long memberId = memberDao.insert(new Member("asdfafs@naver.com", "testPW", "test"));
        Long itemId1 = itemDao.insert(new Item("note1", "image1@com", 10000));
        Long itemId2 = itemDao.insert(new Item("note2", "image2@com", 20000));
        cartDao.insert(memberId, itemId1);
        cartDao.insert(memberId, itemId2);

        List<ItemEntity> memberItems = cartDao.findAllByMemberId(memberId);

        assertThat(memberItems).hasSize(2);
    }

    @Test
    @DisplayName("회원 ID와 상품 ID에 해당하는 값을 삭제한다.")
    void deleteSuccess() {
        Long memberId = memberDao.insert(new Member("asdfafs@naver.com", "testPW", "test"));
        Long itemId = itemDao.insert(new Item("note1", "image1@com", 10000));
        cartDao.insert(memberId, itemId);

        assertDoesNotThrow(() -> cartDao.delete(memberId, itemId));
    }
}
