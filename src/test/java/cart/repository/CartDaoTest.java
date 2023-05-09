package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.domain.product.ImageUrl;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.domain.product.ProductName;
import cart.dto.cart.CartProductDto;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {
    private static final Member MEMBER = new Member(new Email("glenfiddich@naver.com"), new Password("123456"));
    private static final Product PRODUCT1 = new Product(new ProductName("글렌피딕 12년"), new Price(10_000), ImageUrl.from("https://image.com/image.png"));
    private static final Product PRODUCT2 = new Product(new ProductName("글렌리벳 12년"), new Price(20_000), ImageUrl.from("https://image.com/image2.png"));

    @Autowired
    JdbcTemplate jdbcTemplate;

    CartDao cartDao;
    MemberDao memberDao;
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberDao = new MemberDao(jdbcTemplate);
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 사용자의 ID로 상품의 ID가 저장되어야 한다.")
    void save_success() {
        // given
        MemberEntity member = memberDao.save(MEMBER);
        ProductEntity product1 = productDao.save(PRODUCT1);
        ProductEntity product2 = productDao.save(PRODUCT2);

        // when
        cartDao.save(member.getId(), product1.getId());
        cartDao.save(member.getId(), product2.getId());

        // then
        List<CartProductDto> allProducts = cartDao.findAllCartProductByMemberId(member.getId());
        assertThat(allProducts)
                .hasSize(2);
    }

    @Test
    @DisplayName("장바구니에 사용자의 ID와 상품의 ID로 상품이 제거되어야 한다.")
    void delete_success() {
        // given
        MemberEntity member = memberDao.save(MEMBER);
        ProductEntity product1 = productDao.save(PRODUCT1);
        ProductEntity product2 = productDao.save(PRODUCT2);
        cartDao.save(member.getId(), product1.getId());
        cartDao.save(member.getId(), product2.getId());

        // when
        cartDao.delete(member.getId(), product1.getId());

        // then
        List<CartProductDto> allProducts = cartDao.findAllCartProductByMemberId(member.getId());
        assertThat(allProducts)
                .hasSize(1);
    }
}
