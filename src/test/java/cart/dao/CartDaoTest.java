package cart.dao;

import cart.entity.CartEntity;
<<<<<<< HEAD
=======
import cart.entity.MemberEntity;
>>>>>>> db0c1803 (feat: CartDao save 테스트)
import cart.entity.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
=======
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
>>>>>>> db0c1803 (feat: CartDao save 테스트)

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/data-test.sql")
=======

@JdbcTest
>>>>>>> db0c1803 (feat: CartDao save 테스트)
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private ProductDao productDao;
<<<<<<< HEAD
=======
    private MemberDao memberDao;
>>>>>>> db0c1803 (feat: CartDao save 테스트)

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
<<<<<<< HEAD
=======
        this.memberDao = new MemberDao(jdbcTemplate);
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    }

    @DisplayName("장바구니에 상품을 생성한다.")
    @Test
    void create() {
        // given
<<<<<<< HEAD
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));

        final Long memberId = 1L;
        final Long productId = 1L;

        // when, then
        assertDoesNotThrow(() -> cartDao.save(memberId, productId));
    }

    @DisplayName("장바구니에 있는 모든 상품을 조회한다.")
    @Test
    void findAll() {
        // given
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));
        productDao.create(new ProductEntity(2L, "상품", "img", 1000));

        // when
        cartDao.save(1L, 1L);
        cartDao.save(2L, 2L);
=======
        saveMember();
        saveProduct();
        final Long memberId = 1L;
        final Long productId = 2L;

        // when
        cartDao.save(memberId, productId);
>>>>>>> db0c1803 (feat: CartDao save 테스트)

        // then
        List<CartEntity> responses = cartDao.findAll();
        assertAll(
<<<<<<< HEAD
                () -> Assertions.assertThat(responses).hasSize(2),
                () -> Assertions.assertThat(responses.get(0).getMemberId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(0).getProductId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(1).getMemberId()).isEqualTo(2L),
                () -> Assertions.assertThat(responses.get(1).getProductId()).isEqualTo(2L)
        );
    }

    @DisplayName("현재 로그인한 유저의 장바구니에 있는 모든 상품을 조회한다.")
    @Test
    void findAllByMemberId() {
        // given
        productDao.create(new ProductEntity(1L, "상품1", "img", 1000));
        productDao.create(new ProductEntity(2L, "상품2", "img", 2000));
        productDao.create(new ProductEntity(3L, "상품3", "img", 3000));


        // when
        cartDao.save(1L, 1L);
        cartDao.save(1L, 2L);
        List<ProductEntity> responses = cartDao.findAllByMemberId(1L);

        // then
        assertAll(
                () -> Assertions.assertThat(responses).hasSize(2),
                () -> Assertions.assertThat(responses.get(0).getName()).isEqualTo("상품1"),
                () -> Assertions.assertThat(responses.get(0).getPrice()).isEqualTo(1000),
                () -> Assertions.assertThat(responses.get(1).getName()).isEqualTo("상품2"),
                () -> Assertions.assertThat(responses.get(1).getPrice()).isEqualTo(2000)
        );
    }

    @DisplayName("장바구니에 있는 상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        productDao.create(new ProductEntity(1L, "상품1", "img", 1000));
        cartDao.save(1L, 1L);

        // when
        assertDoesNotThrow(() -> cartDao.deleteById(1L));
=======
                () -> Assertions.assertThat(responses).hasSize(1),
                () -> Assertions.assertThat(responses.get(0).getMemberId()).isEqualTo(1L),
                () -> Assertions.assertThat(responses.get(0).getProductId()).isEqualTo(2L)
        );
    }

    private void saveMember() {
        memberDao.create(new MemberEntity(1L, "a@a.com", "1234"));
    }

    private void saveProduct() {
        productDao.create(new ProductEntity(1L, "상품", "img", 1000));
>>>>>>> db0c1803 (feat: CartDao save 테스트)
    }

}
