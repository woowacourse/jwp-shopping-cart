package cart.persistence;

import cart.service.member.domain.Member;
import cart.service.product.domain.Product;
import cart.service.product.domain.ProductImage;
import cart.service.product.domain.ProductName;
import cart.service.product.domain.ProductPrice;
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
        Product product = productDao.findById(productId).get();

        assertThat(cartDao.addCartItem(product, member)).isPositive();
    }

    @Test
    void 장바구니에_있는_유저의_모든_상품_조회() {
        //given
        Member member = memberDao.save(new Member("cyh6099@gmail.com", "qwer1234"));
        Long memberId = member.getId();

        Member memberNoHaveCartItem = memberDao.save(new Member("cyh6099@wooteco.com", "qwer1234"));


        Long chickenId = productDao.save(new Product(new ProductName("chicken"), new ProductImage("image"), new ProductPrice(1000)));
        Long pizzaId = productDao.save(new Product(new ProductName("pizza"), new ProductImage("image"), new ProductPrice(2000)));

        //when
        Product chicken = productDao.findById(chickenId).get();
        Product pizza = productDao.findById(pizzaId).get();

        cartDao.addCartItem(chicken, member);
        cartDao.addCartItem(pizza, member);

        //then
        Assertions.assertAll(
                () -> assertThat(cartDao.findCartItemsByMember(member).getCartItems()).hasSize(2),
                () -> assertThat(cartDao.findCartItemsByMember(memberNoHaveCartItem).getCartItems()).hasSize(0)
        );
    }

    @Test
    void 장바구니에_있는_아이템을_삭제한다() {
        Member member = memberDao.save(new Member("cyh6099@gmail.com", "qwer1234"));

        Long chickenId = productDao.save(new Product(new ProductName("chicken"), new ProductImage("image"), new ProductPrice(1000)));
        Long pizzaId = productDao.save(new Product(new ProductName("pizza"), new ProductImage("image"), new ProductPrice(2000)));

        Product chicken = productDao.findById(chickenId).get();
        Product pizza = productDao.findById(pizzaId).get();

        cartDao.addCartItem(pizza, member);
        cartDao.addCartItem(chicken, member);

        Long chickenCartItemId = cartDao.findOneCartItem(member, chickenId).get();

        cartDao.deleteCartItem(chickenCartItemId);
        assertThat(cartDao.findCartItemsByMember(member).getCartItems()).hasSize(1);


    }
}
