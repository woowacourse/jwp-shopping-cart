package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.entity.Member;
import cart.entity.Product;
import cart.entity.ProductCart;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2ProductCartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductCartDao productCartDao;
    private Member member;
    private Product product;

    @BeforeEach
    void setUp() {
        productCartDao = new H2ProductCartDao(jdbcTemplate);
        ProductDao productDao = new H2ProductDao(jdbcTemplate);
        MemberDao memberDao = new H2MemberDao(jdbcTemplate);

        member = memberDao.save(new Member("boxster@email.com", "boxster"));
        product = productDao.save(new Product("피자", "https://pizza.com", 100000));
    }

    @DisplayName("cart를 저장할 수 있다")
    @Test
    void saveCartTest() {
        ProductCart productCart = new ProductCart(product.getId(), member.getId());

        ProductCart savedCart = productCartDao.save(productCart);

        ProductCart findCart = productCartDao.findById(savedCart.getId()).get();
        assertAll(
                () -> assertThat(savedCart.getId()).isEqualTo(findCart.getId()),
                () -> assertThat(savedCart.getProductId()).isEqualTo(findCart.getProductId()),
                () -> assertThat(savedCart.getMemberId()).isEqualTo(findCart.getMemberId())
        );
    }

    @DisplayName("member로 저장된 모든 product cart를 찾는다")
    @Test
    void findAllByMemberTest() {
        ProductCart productCart = new ProductCart(product.getId(), member.getId());
        ProductCart savedCart = productCartDao.save(productCart);

        List<ProductCart> productCarts = productCartDao.findAllByMember(member);
        assertAll(
                () -> assertThat(productCarts).hasSize(1),
                () -> assertThat(productCarts).containsExactly(savedCart)
        );
    }

}
