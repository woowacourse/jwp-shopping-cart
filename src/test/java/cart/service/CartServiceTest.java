package cart.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.exception.DbNotAffectedException;
import cart.persistence.CartProduct;
import cart.persistence.dao.CartDao;
import cart.persistence.dao.MemberDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Cart;
import cart.persistence.entity.Member;
import cart.persistence.entity.Product;
import cart.service.CartService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CartServiceTest {

    private final CartDao cartDao;
    private final MemberDao memberDao;
    private final ProductDao productDao;
    private final CartService cartService;

    @Autowired
    public CartServiceTest(final CartDao cartDao, final MemberDao memberDao, final ProductDao productDao,
        final CartService cartService) {
        this.cartDao = cartDao;
        this.memberDao = memberDao;
        this.productDao = productDao;
        this.cartService = cartService;
    }

    @Test
    void 유효한_사용자_정보로_장바구니에_상품을_저장하고_조회한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));
        final long chickenId = productDao.save(new Product("chicken", 30000, "https://a.com"));

        assertAll(() -> {
            assertDoesNotThrow(() -> cartService.addProductByEmail(chickenId, email));
            List<CartProduct> products = cartService.findProductsByEmail(email);
            assertThat(products.size()).isEqualTo(1);
        });
    }

    @Test
    void 틀린_이메일로_카트_저장하면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));
        final long chickenId = productDao.save(new Product("chicken", 30000, "https://a.com"));

        assertThrows(IllegalArgumentException.class,
            () -> cartService.addProductByEmail(chickenId, "b@a.com"));
    }

    @Test
    void 틀린_이메일로_카트를_조회하면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));

        assertThrows(IllegalArgumentException.class,
            () -> cartService.findProductsByEmail("b@a.com"));
    }

    @Test
    void deleteByCartId_메서드로_카트의_특정_상품을_삭제한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));
        final long savedId = cartDao.save(new Cart(1, 1));

        assertDoesNotThrow(() -> cartService.deleteByCartId(savedId));
    }

    @Test
    void deleteByCartId_메서드로_삭제된_상품이_없다면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new Member(email, password));
        assertThrows(DbNotAffectedException.class, () -> cartService.deleteByCartId(1L));
    }
}
