package cart.dao;

import cart.dao.entity.CartEntity;
import cart.dao.entity.MemberEntity;
import cart.dao.entity.ProductEntity;
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

import static org.assertj.core.api.Assertions.assertThat;
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
        final Long memberId = insertMember(new MemberEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));

        //when
        final Long id = cartDao.insert(new CartEntity(memberId, productId));

        //then
        assertSoftly(softly -> {
            softly.assertThat(id).isNotNull();
            final CartEntity carEntity = cartDao.findById(id);
            softly.assertThat(carEntity.getMemberId()).isEqualTo(memberId);
            softly.assertThat(carEntity.getProductId()).isEqualTo(productId);
        });
    }

    private Long insertMember(final MemberEntity memberEntity) {
        final String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, memberEntity.getEmail());
            preparedStatement.setString(2, memberEntity.getPassword());
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

    @Test
    void id로_데이터를_찾는다() {
        //given
        final Long memberId = insertMember(new MemberEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));
        final Long id = cartDao.insert(new CartEntity(memberId, productId));

        //when
        final CartEntity cartEntity = cartDao.findById(id);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntity.getMemberId()).isEqualTo(memberId);
            softly.assertThat(cartEntity.getProductId()).isEqualTo(productId);
        });
    }

    @Test
    void 사용자_id로_데이터를_찾는다() {
        //given
        final Long memberId = insertMember(new MemberEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));
        final Long id = cartDao.insert(new CartEntity(memberId, productId));

        //when
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(memberId);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntities).hasSize(1);
            final CartEntity cartEntity = cartEntities.get(0);
            softly.assertThat(cartEntity.getId()).isEqualTo(id);
            softly.assertThat(cartEntity.getMemberId()).isEqualTo(memberId);
            softly.assertThat(cartEntity.getProductId()).isEqualTo(productId);
        });
    }

    @Test
    void 사용자_id와_상품_id로_데이터를_삭제한다() {
        //given
        final Long memberId = insertMember(new MemberEntity("huchu@woowahan.com", "1234567a!"));
        final Long productId = insertProduct(new ProductEntity("치킨", 10_000, "치킨 사진"));
        final Long id = cartDao.insert(new CartEntity(memberId, productId));

        //when
        final int affectedRows = cartDao.delete(memberId, productId);

        //then
        assertThat(affectedRows).isEqualTo(1);
    }
}
