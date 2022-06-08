package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.helper.fixture.MemberFixture.EMAIL;
import static woowacourse.helper.fixture.MemberFixture.NAME;
import static woowacourse.helper.fixture.MemberFixture.PASSWORD;
import static woowacourse.helper.fixture.MemberFixture.createMember;
import static woowacourse.helper.fixture.ProductFixture.IMAGE;
import static woowacourse.helper.fixture.ProductFixture.PRICE;
import static woowacourse.helper.fixture.ProductFixture.createProduct;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.helper.fixture.ProductFixture;
import woowacourse.member.dao.MemberDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.UpdateQuantityRequest;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    ProductDao productDao;

    @Autowired
    MemberDao memberDao;

    @DisplayName("물품을 장바구니에 담는다.")
    @Test
    void save() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));

        Long cartId = cartService.addCart(productId, memberId);

        assertThat(cartId).isNotNull();
    }

    @DisplayName("물품을 장바구니에 두 번 담으면 수량을 업데이트 한다.")
    @Test
    void saveAgain() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));

        cartService.addCart(productId, memberId);
        cartService.addCart(productId, memberId);

        assertThat(cartService.findCartsByMemberId(memberId).get(0).getQuantity()).isEqualTo(2);
    }

    @DisplayName("장바구니 물품들을 조회한다.")
    @Test
    void findCartsByMemberId() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        cartService.addCart(productId, memberId);

        assertThat(cartService.findCartsByMemberId(memberId).size()).isEqualTo(1);
    }

    @DisplayName("장바구니 물품의 수량을 업데이트한다.")
    @Test
    void updateCartItemQuantity() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long cartId = cartService.addCart(productId, memberId);

        UpdateQuantityRequest request = new UpdateQuantityRequest(5);
        cartService.updateCartItemQuantity(cartId, request);

        assertThat(cartService.findCartsByMemberId(memberId).get(0).getQuantity()).isEqualTo(5);
    }

    @DisplayName("장바구니 물품을 삭제한다.")
    @Test
    void deleteCart() {
        Long productId = productDao.save(createProduct(ProductFixture.NAME, PRICE, IMAGE));
        Long memberId = memberDao.save(createMember(EMAIL, PASSWORD, NAME));
        Long cartId = cartService.addCart(productId, memberId);

        cartService.deleteCart(memberId, cartId);

        assertThat(cartService.findCartsByMemberId(memberId).size()).isEqualTo(0);
    }
}
