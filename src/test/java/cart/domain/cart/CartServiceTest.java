package cart.domain.cart;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import cart.domain.exception.DbNotAffectedException;
import cart.domain.exception.UnexpectedDomainException;
import cart.domain.persistence.ProductDto;
import cart.domain.persistence.dao.CartDao;
import cart.domain.persistence.dao.MemberDao;
import cart.domain.persistence.dao.ProductDao;
import cart.domain.persistence.entity.CartEntity;
import cart.domain.persistence.entity.MemberEntity;
import cart.domain.persistence.entity.ProductEntity;

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
        memberDao.save(new MemberEntity(email, password));
        final long chickenId = productDao.save(new ProductEntity("chicken", 30000, "https://a.com"));

        assertAll(() -> {
            assertDoesNotThrow(() -> cartService.addProductByEmail(chickenId, email));
            List<ProductDto> products = cartService.findProductsByEmail(email);
            assertThat(products.size()).isEqualTo(1);
        });
    }

    @Test
    void 틀린_이메일로_카트_저장하면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final long chickenId = productDao.save(new ProductEntity("chicken", 30000, "https://a.com"));

        assertThrows(UnexpectedDomainException.class,
            () -> cartService.addProductByEmail(chickenId, "b@a.com"));
    }

    @Test
    void 틀린_이메일로_카트를_조회하면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));

        assertThrows(UnexpectedDomainException.class,
            () -> cartService.findProductsByEmail("b@a.com"));
    }

    @Test
    void deleteByCartId_메서드로_카트의_특정_상품을_삭제한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        final long savedId = cartDao.save(new CartEntity(1, 1));

        assertDoesNotThrow(() -> cartService.deleteByCartId(savedId));
    }

    @Test
    void deleteByCartId_메서드로_삭제된_상품이_없다면_예외가_발생한다() {
        final String email = "a@a.com";
        final String password = "password1";
        memberDao.save(new MemberEntity(email, password));
        assertThrows(DbNotAffectedException.class, () -> cartService.deleteByCartId(1L));
    }
}
