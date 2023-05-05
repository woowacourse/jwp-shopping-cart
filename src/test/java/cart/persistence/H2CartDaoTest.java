package cart.persistence;

import cart.service.cart.Cart;
import cart.service.member.Member;
import cart.service.product.Product;
import cart.service.product.ProductImage;
import cart.service.product.ProductName;
import cart.service.product.ProductPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
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

        Long productId = productDao.save(new Product(new ProductName("chicken"), new ProductImage("image"), new ProductPrice(1000)));
        Cart cart = new Cart(productId, memberId);

        assertThat(cartDao.addProduct(cart)).isPositive();
    }

    @Test
    void 장바구니에_있는_유저의_모든_상품_조회() {
        //given
        Member member = memberDao.save(new Member("cyh6099@gmail.com", "qwer1234"));
        Long memberId = member.getId();

        Long memberNoHaveCartId = memberDao.save(new Member("cyh6099@wooteco.com", "qwer1234")).getId();

        Long chickenId = productDao.save(new Product(new ProductName("chicken"), new ProductImage("image"), new ProductPrice(1000)));
        Long pizzaId = productDao.save(new Product(new ProductName("pizza"), new ProductImage("image"), new ProductPrice(2000)));

        //when
        cartDao.addProduct(new Cart(chickenId, memberId));
        cartDao.addProduct(new Cart(pizzaId, memberId));

        //then
        Assertions.assertAll(
                () -> assertThat(cartDao.findProductsByUserId(memberId)).hasSize(2),
                () -> assertThat(cartDao.findProductsByUserId(memberNoHaveCartId)).hasSize(0)
        );
    }

    @Test
    void 장바구니에_있는_아이템을_삭제한다() {
        Member member = memberDao.save(new Member("cyh6099@gmail.com", "qwer1234"));

        Long chickenId = productDao.save(new Product(new ProductName("chicken"), new ProductImage("image"), new ProductPrice(1000)));
        Long pizzaId = productDao.save(new Product(new ProductName("pizza"), new ProductImage("image"), new ProductPrice(2000)));

        Long chickenCartItemId = cartDao.addProduct(new Cart(chickenId, member.getId()));
        cartDao.addProduct(new Cart(pizzaId, member.getId()));

        cartDao.deleteCartItem(chickenCartItemId);

        Assertions.assertAll(
                () -> assertThat(cartDao.findProductsByUserId(member.getId())).hasSize(1),
                () -> assertThat(cartDao.findProductsByUserId(member.getId()).get(0).getName()).isEqualTo("pizza")
        );

    }
}
