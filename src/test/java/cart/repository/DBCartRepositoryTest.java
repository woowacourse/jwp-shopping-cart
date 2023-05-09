package cart.repository;

import cart.entity.CartEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Sql({"/testSchema.sql"})
class DBCartRepositoryTest {

    private final long userId = 1L;
    @Autowired
    JdbcTemplate jdbcTemplate;
    private CartRepository cartRepository;
    private CartEntity entity1;
    private CartEntity entity2;

    @BeforeEach
    void setUp() {
        cartRepository = new DBCartRepository(jdbcTemplate);

        CartEntity entity1 = new CartEntity(null, userId, 1L);
        CartEntity entity2 = new CartEntity(null, userId, 2L);

        this.entity1 = cartRepository.save(entity1);
        this.entity2 = cartRepository.save(entity2);
    }

    @Test
    @DisplayName("회원 id와 상품 id를 cart DB에 저장한다.")
    void saveTest() {
        CartEntity entity3 = new CartEntity(null, userId, 3L);
        cartRepository.save(entity3);

        List<CartEntity> entities = cartRepository.findByUserId(userId);
        assertThat(entities).hasSize(3);
    }

    @Test
    @DisplayName("사용자 id로 cart 정보를 조회한다.")
    void findByUserIdTest() {
        List<CartEntity> entities = cartRepository.findByUserId(userId);
        assertThat(entities).isEqualTo(List.of(entity1, entity2));
    }

    @Test
    @DisplayName("cartId에 해당하는 카트 정보를 삭제한다.")
    void deleteByIdTest() {
        cartRepository.deleteById(userId);
        List<CartEntity> entities = cartRepository.findByUserId(userId);
        assertThat(entities).hasSize(1);
    }
}