package cart.dao;

import cart.entity.CartEntity;
import cart.entity.MemberEntity;
import cart.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private ProductDao productDao;
    private MemberDao memberDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
        this.memberDao = new MemberDao(jdbcTemplate);
    }

    @DisplayName("장바구니에 상품을 생성한다.")
    @Test
    void create() {
        // given
        saveMember();
        saveProduct();
        final Long memberId = 1L;
        final Long productId = 1L;

        // when
        cartDao.save(memberId, productId);

        // then
        List<CartEntity> responses = cartDao.findAll();
        assertAll(
                () -> Assertions.assertThat(responses).hasSize(1),
                () -> Assertions.assertThat(responses.get(0).getMemberId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(0).getProductId()).isEqualTo(1L)
        );
    }

    @DisplayName("장바구니에 있는 모든 상품을 조회한다.")
    @Test
    void findAll() {
        // given
        saveMember();
        saveProduct();

        memberDao.create(new MemberEntity(1L, "a@a.com", "1234"));
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));

        // when
        cartDao.save(1L, 1L);
        cartDao.save(2L, 2L);

        // then
        List<CartEntity> responses = cartDao.findAll();
        assertAll(
                () -> Assertions.assertThat(responses).hasSize(2),
                () -> Assertions.assertThat(responses.get(0).getMemberId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(0).getProductId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(1).getMemberId()).isEqualTo(2L),
                () -> Assertions.assertThat(responses.get(1).getProductId()).isEqualTo(2L)
        );
    }

    private void saveMember() {
        memberDao.create(new MemberEntity(1L, "a@a.com", "1234"));
    }

    private void saveProduct() {
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));
    }

}
