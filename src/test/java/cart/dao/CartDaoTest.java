package cart.dao;

import cart.dao.entity.CartEntity;
import cart.dao.entity.ProductEntity;
import cart.dao.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class CartDaoTest {

    private static final String[] GENERATED_ID_COLUMN = {"id"};

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
    }

    @Test
    void 데이터를_넣는다() {
        //given
        final Long userId = insertUser(new UserEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));

        //when
        final Long id = cartDao.insert(new CartEntity(userId, productId));

        //then
        assertSoftly(softly -> {
            softly.assertThat(id).isNotNull();
            final CartEntity carEntity = cartDao.findById(id);
            softly.assertThat(carEntity.getUserId()).isEqualTo(userId);
            softly.assertThat(carEntity.getProductId()).isEqualTo(productId);
        });
    }

    @Test
    void id로_데이터를_찾는다() {
        //given
        final Long userId = insertUser(new UserEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));
        final Long id = cartDao.insert(new CartEntity(userId, productId));

        //when
        final CartEntity cartEntity = cartDao.findById(id);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntity.getUserId()).isEqualTo(userId);
            softly.assertThat(cartEntity.getProductId()).isEqualTo(productId);
        });
    }

    @Test
    void 사용자_id로_데이터를_찾는다() {
        //given
        final Long userId = insertUser(new UserEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));
        final Long id = cartDao.insert(new CartEntity(userId, productId));

        //when
        final List<CartEntity> cartEntities = cartDao.findAllByUserId(userId);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntities).hasSize(1);
            final CartEntity cartEntity = cartEntities.get(0);
            softly.assertThat(cartEntity.getId()).isEqualTo(id);
            softly.assertThat(cartEntity.getUserId()).isEqualTo(userId);
            softly.assertThat(cartEntity.getProductId()).isEqualTo(productId);
        });
    }

    private Long insertUser(final UserEntity userEntity) {
        final String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, userEntity.getEmail());
            preparedStatement.setString(2, userEntity.getPassword());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private Long insertProduct(final ProductEntity productEntity) {
        final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, productEntity.getName());
            preparedStatement.setInt(2, productEntity.getPrice());
            preparedStatement.setString(3, productEntity.getImage());
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
}
