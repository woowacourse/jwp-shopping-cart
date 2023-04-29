package cart.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Member;
import cart.domain.Product;
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
        MemberEntity member = memberDao.save(new Member("glenfiddich@naver.com", "123456"));
        ProductEntity product1 = productDao.save(new Product("글렌피딕 12년", 10_000, "image1.jpg"));
        ProductEntity product2 = productDao.save(new Product("글렌리벳 12년", 20_000, "image2.jpg"));

        // when
        cartDao.save(member.getId(), product1.getId());
        cartDao.save(member.getId(), product2.getId());

        // then
        List<CartProductDto> allProducts = cartDao.findAllProductByMemberId(member.getId());
        assertThat(allProducts)
                .hasSize(2);
    }

    @Test
    @DisplayName("장바구니에 사용자의 ID와 상품의 ID로 상품이 제거되어야 한다.")
    void delete_success() {
        // given
        MemberEntity member = memberDao.save(new Member("glenfiddich@naver.com", "123456"));
        ProductEntity product1 = productDao.save(new Product("글렌피딕 12년", 10_000, "image1.jpg"));
        ProductEntity product2 = productDao.save(new Product("글렌리벳 12년", 20_000, "image2.jpg"));
        cartDao.save(member.getId(), product1.getId());
        cartDao.save(member.getId(), product2.getId());

        // when
        cartDao.delete(member.getId(), product1.getId());

        // then
        List<CartProductDto> allProducts = cartDao.findAllProductByMemberId(member.getId());
        assertThat(allProducts)
                .hasSize(1);
    }
}
