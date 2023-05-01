package cart.dao;

import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private Long savedMemberId;
    private Long savedProductId;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        final MemberDao memberDao = new MemberDao(jdbcTemplate);
        final ProductDao productDao = new ProductDao(jdbcTemplate);

        savedMemberId = memberDao.findAll().get(0).getId();
        savedProductId = productDao.save(new ProductEntity(
                "name",
                "imageUrl",
                10000,
                "description"
        ));
    }

    @Nested
    @DisplayName("장바구니 ID로 조회 시")
    class FindById {

        @Test
        @DisplayName("장바구니가 존재하면 장바구니를 반환한다.")
        void findById() {
            final Long savedCartId = cartDao.save(new CartEntity(savedMemberId, savedProductId));

            final Optional<CartEntity> result = cartDao.findById(savedCartId);

            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.get().getId()).isEqualTo(savedCartId)
            );
        }

        @Test
        @DisplayName("장바구니가 존재하지 않으면 반환하지 않는다.")
        void findByIdWithEmpty() {
            final Optional<CartEntity> result = cartDao.findById(1L);

            assertThat(result).isEmpty();
        }
    }

    @Test
    @DisplayName("장바구니 목록을 조회한다.")
    void findAllByMemberId() {
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);
        final Long savedCartId = cartDao.save(cartEntity);

        final List<CartEntity> result = cartDao.findAllByMemberId(savedMemberId);

        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getId()).isEqualTo(savedCartId),
                () -> assertThat(result.get(0).getMemberId()).isEqualTo(savedMemberId),
                () -> assertThat(result.get(0).getProductId()).isEqualTo(savedProductId)
        );
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void save() {
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);

        final Long savedCartId = cartDao.save(cartEntity);

        assertThat(savedCartId).isNotNull();
    }


    @Test
    @DisplayName("장바구니 상품을 삭제한다.")
    void delete() {
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);
        final Long savedCartId = cartDao.save(cartEntity);

        cartDao.delete(savedCartId);

        final List<CartEntity> result = cartDao.findAllByMemberId(savedMemberId);
        assertThat(result).isEmpty();
    }
}
