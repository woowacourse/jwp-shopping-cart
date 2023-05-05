package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.controller.dto.ItemResponse;
import cart.dao.CartDao;
import cart.dao.ItemDao;
import cart.dao.MemberDao;
import cart.domain.Item;
import cart.domain.Member;
import cart.exception.CartDuplicateException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    CartService cartService;
    CartDao cartDao;
    MemberDao memberDao;
    ItemDao itemDao;

    @BeforeEach
    void setUp() {
        cartService = new CartService(new CartDao(jdbcTemplate));
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        itemDao = new ItemDao(jdbcTemplate);
    }

    @Test
    @DisplayName("회원 ID에 해당하는 장바구니 상품을 모두 가져온다.")
    void findAllByMember() {
        Long memberId = saveMember();
        Long itemId = saveItem();
        cartDao.insert(memberId, itemId);

        List<ItemResponse> memberItems = cartService.findAllByMemberId(memberId);

        assertThat(memberItems).hasSize(1);
    }

    @Test
    @DisplayName("이미 장바구니에 추가된 경우 예외가 발생한다.")
    void saveFailWithDupItem() {
        Long memberId = saveMember();
        Long itemId = saveItem();
        cartDao.insert(memberId, itemId);

        assertThatThrownBy(() -> cartService.save(memberId, itemId))
                .isInstanceOf(CartDuplicateException.class)
                .hasMessageContaining("이미 장바구니에 추가된 상품입니다.");
    }

    @Test
    @DisplayName("회원 ID와 상품 ID에 해당하는 상품을 삭제한다.")
    void deleteSuccess() {
        Long memberId = saveMember();
        Long itemId = saveItem();
        cartDao.insert(memberId, itemId);

        assertDoesNotThrow(() -> cartService.delete(memberId, itemId));
    }

    private Long saveMember() {
        Member member = new Member("test@test.com", "1234", "gray");
        return memberDao.insert(member);
    }

    private Long saveItem() {
        Item item = new Item("testItem", "test.com", 10000);
        return itemDao.insert(item);
    }
}
