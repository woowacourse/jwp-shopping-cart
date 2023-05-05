package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.domain.Product;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);

        String deleteAllSql = "DELETE FROM cart";
        jdbcTemplate.update(deleteAllSql);
    }

    @DisplayName("장바구니에 상품을 저장한다.")
    @Test
    void saveCartItemByMemberEmail() {
        // when
        Long savedId = cartDao.saveCartItemByMemberEmail("jeomxon@gmail.com", 1L);

        // then
        assertThat(savedId).isEqualTo(1);
    }

    @DisplayName("이메일에 해당되는 멤버의 장바구니의 모든 상품을 조회한다.")
    @Test
    void findCartItemsByMemberEmail() {
        // given
        String memberEmail = "jeomxon@gmail.com";

        cartDao.saveCartItemByMemberEmail(memberEmail, 1L);
        cartDao.saveCartItemByMemberEmail(memberEmail, 2L);
        cartDao.saveCartItemByMemberEmail(memberEmail, 3L);

        // when
        List<Product> products = cartDao.findCartItemsByMemberEmail(memberEmail);

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).hasSize(3);
            softly.assertThat(products.get(0).getName()).isEqualTo("치킨");
            softly.assertThat(products.get(0).getPrice()).isEqualTo(10000);
            softly.assertThat(products.get(1).getName()).isEqualTo("샐러드");
            softly.assertThat(products.get(1).getPrice()).isEqualTo(20000);
            softly.assertThat(products.get(2).getName()).isEqualTo("피자");
            softly.assertThat(products.get(2).getPrice()).isEqualTo(13000);
        });
    }

    @DisplayName("CartId로 상품을 삭제한다.")
    @Test
    void deleteCartItemById() {
        // given
        String memberEmail = "jeomxon@gmail.com";

        Long firstSavedId = cartDao.saveCartItemByMemberEmail(memberEmail, 1L);
        cartDao.saveCartItemByMemberEmail(memberEmail, 2L);

        // when
        cartDao.deleteCartItemById(firstSavedId);
        List<Product> products = cartDao.findCartItemsByMemberEmail(memberEmail);

        // then
        assertSoftly(softly -> {
            softly.assertThat(products).hasSize(1);
            softly.assertThat(products.get(0).getName()).isEqualTo("샐러드");
            softly.assertThat(products.get(0).getPrice()).isEqualTo(20000);
        });
    }
}
