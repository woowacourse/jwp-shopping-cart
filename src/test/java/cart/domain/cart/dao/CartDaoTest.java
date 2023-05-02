package cart.domain.cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.entity.Cart;
import cart.domain.member.dao.MemberDao;
import cart.domain.member.entity.Member;
import cart.domain.product.dao.ProductDao;
import cart.domain.product.entity.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("장바구니에 상품을 저장한다.")
    public void testSave() {
        //given
        final CartDao cartDao = new CartDao(jdbcTemplate);
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Member member = new Member(1L, "test@test.com", "password", LocalDateTime.now(),
            LocalDateTime.now());
        final Product product = new Product(1L, "product", 1000, "imageUrl", LocalDateTime.now(),
            LocalDateTime.now());
        memberDao.save(member);
        productDao.save(product);
        final Cart cart = new Cart(null, product, member, null, null);

        //when
        final Cart result = cartDao.save(cart);

        //then
        final List<Cart> products = cartDao.findByMember(member);
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getMember().getId()).isEqualTo(result.getMember().getId());
        assertThat(products.get(0).getMember().getId()).isEqualTo(result.getProduct().getId());
    }
}
