package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Cart;
import cart.domain.Member;
import cart.domain.Product;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@JdbcTest
class CartDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert memberJdbcInsert;

    private SimpleJdbcInsert productJdbcInsert;

    private CartDao cartDao;

    @BeforeEach
    void setUp() {
        cartDao = new CartDao(jdbcTemplate);
        memberJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("member")
                .usingColumns("email", "password")
                .usingGeneratedKeyColumns("id");
        productJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingColumns("name", "image", "price")
                .usingGeneratedKeyColumns("id");
    }

    @Test
    void 장바구니에_상품을_추가한다() {
        // given
        final Long memberId = insertMember("pizza@pizza.com", "password");
        final Long productId = insertProduct("치즈피자", "1.jpg", 8900L);
        final Cart cart = new Cart(memberId, productId);

        // when
        final Long id = cartDao.saveAndGetId(cart);

        // then
        final List<Product> result = cartDao.findAllProductByMemberId(memberId);
        assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(id).isPositive()
        );
    }

    private Long insertProduct(final String name, final String image, final Long price) {
        final Product product = new Product(name, image, price);
        final BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(product);
        return productJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    private Long insertMember(final String email, final String password) {
        final Member member = new Member(email, password);
        final BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(member);
        return memberJdbcInsert.executeAndReturnKey(parameterSource).longValue();
    }

    @Test
    void 사용자_아이디를_받아_해당_사용자의_장바구니에_있는_모든_품목을_반환한다() {
        // given
        final Long memberId1 = insertMember("pizza1@pizza.com", "password1");
        final Long memberId2 = insertMember("pizza2@pizza.com", "password2");
        final Long productId1 = insertProduct("치즈피자", "1.jpg", 8900L);
        final Long productId2 = insertProduct("치즈피자2", "2.jpg", 18900L);
        final Long productId3 = insertProduct("치즈피자3", "3.jpg", 18900L);
        cartDao.saveAndGetId(new Cart(memberId1, productId1));
        cartDao.saveAndGetId(new Cart(memberId1, productId2));
        cartDao.saveAndGetId(new Cart(memberId2, productId3));

        // when
        final List<Product> result = cartDao.findAllProductByMemberId(memberId1);

        // then
        assertThat(result).usingRecursiveComparison().ignoringFieldsOfTypes(LocalDateTime.class).isEqualTo(List.of(
                new Product(productId1, "치즈피자", "1.jpg", 8900L),
                new Product(productId2, "치즈피자2", "2.jpg", 18900L)
        ));
    }
}
