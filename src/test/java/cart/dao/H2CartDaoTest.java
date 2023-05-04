package cart.dao;

import cart.domain.Product;
import cart.domain.cart.Cart;
import cart.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2CartDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    H2CartDao cartDao;
    H2MemberDao memberDao;
    H2ProductDao productDao;

    @BeforeEach
    void setUp() {
        cartDao = new H2CartDao(jdbcTemplate);
        memberDao = new H2MemberDao(jdbcTemplate);
        productDao = new H2ProductDao(jdbcTemplate);
    }

    @Test
    void 장바구니에_상품을_담는다() {
        Member member = memberDao.save(new Member("cyh6099@gmail.com", "qwer1234"));
        Long memberId = member.getId();

        Long productId = productDao.save(new Product("chicken", "image", 1000));
        Cart cart = new Cart(productId, memberId);

        Assertions.assertThat(cartDao.addProduct(cart)).isPositive();
    }
}
