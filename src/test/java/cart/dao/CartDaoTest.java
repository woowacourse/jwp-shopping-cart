package cart.dao;

import cart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.jdbc.Sql;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
@Sql("classpath:data-test.sql")
class CartDaoTest {

    private static final String[] GENERATED_ID_COLUMN = {"id"};
    private static final String EMAIL = "huchu@woowahan.com";
    private static final String PASSWORD = "1234567a!";
    private static final String NAME = "치킨";
    private static final int PRICE = 10_000;
    private static final String IMAGE = "치킨 사진";

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
        final Long memberId = insertMember(EMAIL, PASSWORD);
        final Long productId = insertProduct(NAME, PRICE, IMAGE);

        //when
        final Long id = cartDao.insert(new CartEntity(
                new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)),
                new ProductEntity(productId, NAME, PRICE, IMAGE)));

        //then
        assertSoftly(softly -> {
            softly.assertThat(id).isNotNull();
            final CartEntity carEntity = cartDao.findById(id);
            softly.assertThat(carEntity.getMember()).isEqualTo(new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)));
            softly.assertThat(carEntity.getProduct()).isEqualTo(new ProductEntity(productId, NAME, PRICE, IMAGE));
        });
    }

    private Long insertMember(final String email, final String password) {
        final String sql = "INSERT INTO member (email, password) VALUES (?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private Long insertProduct(final String name, final Integer price, final String image) {
        final String sql = "INSERT INTO product (name, price, image) VALUES (?, ?, ?)";
        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement preparedStatement = con.prepareStatement(sql, GENERATED_ID_COLUMN);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, price);
            preparedStatement.setString(3, image);
            return preparedStatement;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Test
    void id로_데이터를_찾는다() {
        //given
        final Long memberId = insertMember(EMAIL, PASSWORD);
        final Long productId = insertProduct(NAME, PRICE, IMAGE);
        final Long id = cartDao.insert(new CartEntity(
                new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)),
                new ProductEntity(productId, NAME, PRICE, IMAGE)));

        //when
        final CartEntity cartEntity = cartDao.findById(id);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntity.getMember()).isEqualTo(new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)));
            softly.assertThat(cartEntity.getProduct()).isEqualTo(new ProductEntity(productId, NAME, PRICE, IMAGE));
        });
    }

    @Test
    void 사용자_id로_데이터를_찾는다() {
        //given
        final Long memberId = insertMember(EMAIL, PASSWORD);
        final Long productId = insertProduct(NAME, PRICE, IMAGE);
        final Long id = cartDao.insert(new CartEntity(
                new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)),
                new ProductEntity(productId, NAME, PRICE, IMAGE)));

        //when
        final List<CartEntity> cartEntities = cartDao.findAllByMemberId(memberId);

        //then
        assertSoftly(softly -> {
            softly.assertThat(cartEntities).hasSize(1);
            final CartEntity cartEntity = cartEntities.get(0);
            softly.assertThat(cartEntity.getId()).isEqualTo(id);
            softly.assertThat(cartEntity.getMember()).isEqualTo(new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)));
            softly.assertThat(cartEntity.getProduct()).isEqualTo(new ProductEntity(productId, NAME, PRICE, IMAGE));
        });
    }

    @Test
    void 사용자_id와_상품_id로_데이터를_삭제한다() {
        //given
        final Long memberId = insertMember(EMAIL, PASSWORD);
        final Long productId = insertProduct(NAME, PRICE, IMAGE);
        cartDao.insert(new CartEntity(
                new MemberEntity(memberId, new Email(EMAIL), new Password(PASSWORD)),
                new ProductEntity(productId, NAME, PRICE, IMAGE)));

        //when
        final int affectedRows = cartDao.delete(memberId, productId);

        //then
        assertThat(affectedRows).isEqualTo(1);
    }
}
