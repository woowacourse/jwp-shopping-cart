package cart.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

import cart.dto.AuthInfoRequest;
import cart.repository.dao.JdbcCartDao;
import cart.repository.dao.JdbcMemberDao;
import cart.repository.dao.JdbcProductDao;
import cart.repository.entity.CartEntity;
import cart.repository.entity.MemberEntity;
import cart.repository.entity.ProductEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private JdbcProductDao productDao;

    @Mock
    private JdbcMemberDao memberDao;

    @Mock
    private JdbcCartDao cartDao;

    @Test
    void 사용자의_장바구니에_상품을_추가한다() {
        final Long productId = 1L;
        final AuthInfoRequest authInfoRequest = new AuthInfoRequest("email", "password");
        final MemberEntity memberEntity =
                new MemberEntity(1L, "name", authInfoRequest.getEmail(), authInfoRequest.getPassword());
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        given(memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword()))
                .willReturn(memberEntity);
        given(cartDao.save(cartEntity))
                .willReturn(null);

        assertThatCode(() -> cartService.addProductByAuthInfo(productId, authInfoRequest))
                .doesNotThrowAnyException();
    }

    @Test
    void 사용자의_장바구니에_있는_상품들을_조회한다() {
        final Long productId = 1L;
        final AuthInfoRequest authInfoRequest = new AuthInfoRequest("email", "password");
        final MemberEntity memberEntity =
                new MemberEntity(1L, "name", authInfoRequest.getEmail(), authInfoRequest.getPassword());
        final CartEntity cartEntity = new CartEntity(memberEntity.getId(), productId);
        final ProductEntity productEntity = new ProductEntity(productId, "name", "imageUrl", 1000);
        given(memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword()))
                .willReturn(memberEntity);
        given(cartDao.findByMemberId(memberEntity.getId()))
                .willReturn(List.of(cartEntity));
        given(productDao.findById(cartEntity.getProductId()))
                .willReturn(productEntity);

        final List<ProductEntity> productEntities = cartService.findProductsByAuthInfo(authInfoRequest);

        final SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(productEntities.size()).isOne();
        softAssertions.assertThat(productEntities.get(0).getName()).isEqualTo("name");
        softAssertions.assertThat(productEntities.get(0).getImageUrl()).isEqualTo("imageUrl");
        softAssertions.assertThat(productEntities.get(0).getPrice()).isEqualTo(1000);
        softAssertions.assertAll();
    }

    @Test
    void 사용자의_장바구니에서_상품을_삭제한다() {
        final Long productId = 1L;
        final AuthInfoRequest authInfoRequest = new AuthInfoRequest("email", "password");
        final MemberEntity memberEntity =
                new MemberEntity(1L, "name", authInfoRequest.getEmail(), authInfoRequest.getPassword());
        given(memberDao.findByEmailAndPassword(authInfoRequest.getEmail(), authInfoRequest.getPassword()))
                .willReturn(memberEntity);
        given(cartDao.delete(memberEntity.getId(), productId))
                .willReturn(1);

        assertThatCode(() -> cartService.deleteProductByAuthInfo(productId, authInfoRequest))
                .doesNotThrowAnyException();
    }
}
