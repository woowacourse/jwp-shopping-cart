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
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/test-data.sql")
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
        final Member savedMember = memberDao.save(member);
        final Product savedProduct = productDao.save(product);
        final Cart cart = new Cart(null, savedProduct, savedMember, null, null);

        //when
        final Cart result = cartDao.save(cart);

        //then
        final List<Cart> products = cartDao.findByMember(savedMember);
        assertThat(products.size()).isEqualTo(1);
        assertThat(products.get(0).getMember().getId()).isEqualTo(result.getMember().getId());
        assertThat(products.get(0).getMember().getId()).isEqualTo(result.getProduct().getId());
    }

    @Test
    @DisplayName("회원 별 장바구니를 조회한다.")
    public void testFindByMember() {
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
        final Cart savedCart = cartDao.save(cart);

        //when
        final List<Cart> carts = cartDao.findByMember(member);

        //then
        assertThat(carts.size()).isEqualTo(1);
        assertThat(carts.get(0).getMember().getId()).isEqualTo(member.getId());
        assertThat(carts.get(0).getProduct().getId()).isEqualTo(product.getId());
        assertThat(carts.get(0).getId()).isEqualTo(savedCart.getId());
    }

    @Test
    @DisplayName("장바구니에서 제외한다.")
    public void testDelete() {
        //given
        final CartDao cartDao = new CartDao(jdbcTemplate);
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final ProductDao productDao = new ProductDao(jdbcTemplate);
        final Member member = new Member(1L, "test@test.com", "password", LocalDateTime.now(),
            LocalDateTime.now());
        final Product product = new Product(1L, "product", 1000, "imageUrl", LocalDateTime.now(),
            LocalDateTime.now());
        final Member savedMember = memberDao.save(member);
        final Product savedProduct = productDao.save(product);
        final Cart cart = new Cart(null, savedProduct, savedMember, null, null);
        final Cart savedCart = cartDao.save(cart);

        //when
        cartDao.deleteById(savedCart.getId());

        //then
        final List<Cart> carts = cartDao.findByMember(savedMember);
        assertThat(carts.size()).isEqualTo(0);
    }
}
