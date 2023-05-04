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
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));

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
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));
        productDao.create(new ProductEntity(2L, "상품", "img", 1000));

        // when
        cartDao.save(3L, 2L);
        cartDao.save(4L, 3L);

        // then
        List<CartEntity> responses = cartDao.findAll();
        assertAll(
                () -> Assertions.assertThat(responses).hasSize(2),
                () -> Assertions.assertThat(responses.get(0).getMemberId()).isEqualTo(3L),
                () -> Assertions.assertThat(responses.get(0).getProductId()).isEqualTo(2L),
                () -> Assertions.assertThat(responses.get(1).getMemberId()).isEqualTo(4L),
                () -> Assertions.assertThat(responses.get(1).getProductId()).isEqualTo(3L)
        );
    }

    @DisplayName("현재 로그인한 유저의 장바구니에 있는 모든 상품을 조회한다.")
    @Test
    void findAllByMemberId() {
        // given
        saveMember();
        productDao.create(new ProductEntity(1L, "상품1", "img", 1000));
        productDao.create(new ProductEntity(2L, "상품2", "img", 2000));
        productDao.create(new ProductEntity(3L, "상품3", "img", 3000));


        // when
        cartDao.save(5L, 4L);
        cartDao.save(5L, 5L);
        List<ProductEntity> responses = cartDao.findAllByMemberId(5L);

        // then
        assertAll(
                () -> Assertions.assertThat(responses).hasSize(2),
                () -> Assertions.assertThat(responses.get(0).getName()).isEqualTo("상품1"),
                () -> Assertions.assertThat(responses.get(0).getPrice()).isEqualTo(1000),
                () -> Assertions.assertThat(responses.get(1).getName()).isEqualTo("상품2"),
                () -> Assertions.assertThat(responses.get(1).getPrice()).isEqualTo(2000)
        );
    }

    public void saveMember() {
        memberDao.create(new MemberEntity(1L, "a@a.com", "password"));
        memberDao.create(new MemberEntity(2L, "b@b.com", "password"));
    }

}
