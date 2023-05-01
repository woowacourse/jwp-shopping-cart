package cart.cartitems.dao;

import cart.cartitems.dto.CartItemDto;
import cart.member.dao.MemberDao;
import cart.member.domain.Member;
import cart.product.dao.H2ProductDao;
import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static cart.TestFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class CartItemDaoTest {

    private final ProductDao productDao;
    private final MemberDao memberDao;
    private final CartItemDao cartItemDao;

    @Autowired
    CartItemDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new H2ProductDao(namedParameterJdbcTemplate);
        this.memberDao = new MemberDao(namedParameterJdbcTemplate);
        this.cartItemDao = new CartItemDao(namedParameterJdbcTemplate);
    }

    Member withIdMember1;
    Member withIdMember2;
    Product withIdProduct1;
    Product withIdProduct2;
    Product withIdProduct3;

    @BeforeAll
    void beforeAll() {
        withIdMember1 = memberDao.save(NO_ID_MEMBER1);
        withIdMember2 = memberDao.save(NO_ID_MEMBER2);
        withIdProduct1 = productDao.save(NO_ID_PRODUCT1);
        withIdProduct2 = productDao.save(NO_ID_PRODUCT2);
        withIdProduct3 = productDao.save(NO_ID_PRODUCT3);
    }

    @Test
    @DisplayName("유저가 담은 모든 상품의 아이디를 가져온다")
    void findProductIdsByMemberId() {
        cartItemDao.saveItemOfMember(new CartItemDto(withIdMember1.getId(), withIdProduct1.getId()));
        cartItemDao.saveItemOfMember(new CartItemDto(withIdMember1.getId(), withIdProduct2.getId()));
        cartItemDao.saveItemOfMember(new CartItemDto(withIdMember2.getId(), withIdProduct3.getId()));

        final List<Long> itemsIds = cartItemDao.findProductIdsByMemberId(withIdMember1.getId());

        assertThat(itemsIds).hasSize(2);
    }
}
