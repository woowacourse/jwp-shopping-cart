package cart.dao;

import static cart.fixture.MemberFixtures.INSERT_MEMBER_ENTITY;
import static cart.fixture.ProductFixtures.INSERT_PRODUCT_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import cart.entity.CartEntity;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CartDao cartDao;
    private MemberDao memberDao;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.cartDao = new CartDao(jdbcTemplate);
        this.memberDao = new MemberDao(jdbcTemplate);
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void insert() {
        // given
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);

        // when, then
        assertDoesNotThrow(() ->
                cartDao.insert(new CartEntity.Builder()
                        .memberId(insertedMemberId)
                        .productId(insertedProductId)
                        .build()
                ));
    }

    @Test
    @DisplayName("멤버 ID를 받아서 JOIN하여 멤버 ID가 같은 모든 상품을 반환한다.")
    void selectAllProductByMemberId() {
        // given
        long insertedMemberId = memberDao.insert(INSERT_MEMBER_ENTITY);
        long insertedProductId = productDao.insert(INSERT_PRODUCT_ENTITY);
        cartDao.insert((new CartEntity.Builder()
                .memberId(insertedMemberId)
                .productId(insertedProductId)
                .build()));

        // when
        List<ProductEntity> productEntities = cartDao.selectAllProductByMemberId(insertedMemberId);

        // then
        assertAll(
                () -> assertThat(productEntities).hasSize(1),
                () -> assertThat(productEntities.get(0).getProductId()).isEqualTo(insertedProductId)
        );
    }
}
