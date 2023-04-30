package cart.dao;

import cart.entity.CartEntity;
import cart.entity.product.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

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

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void save() {
        final CartEntity cartEntity = new CartEntity(savedMemberId, savedProductId);

        final Long savedCartId = cartDao.save(cartEntity);

        assertThat(savedCartId).isNotNull();
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
}
