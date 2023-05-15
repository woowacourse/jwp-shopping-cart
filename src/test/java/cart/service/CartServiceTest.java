package cart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cart.dao.cart.CartDao;
import cart.dao.cart.FakeCartDao;
import cart.dao.member.FakeMemberDao;
import cart.dao.product.FakeProductDao;
import cart.dao.product.ProductDao;
import cart.domain.cart.Cart;
import cart.domain.member.Email;
import cart.domain.member.Member;
import cart.domain.member.Password;
import cart.domain.product.Name;
import cart.domain.product.Price;
import cart.domain.product.Product;
import cart.dto.cart.RequestCartDto;
import cart.dto.cart.ResponseCartDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartServiceTest {

    private CartDao cartDao;
    private ProductDao productDao;
    private FakeMemberDao memberDao;

    @BeforeEach
    void setUp() {
        cartDao = new FakeCartDao();
        productDao = new FakeProductDao();
        memberDao = new FakeMemberDao();
    }

    private Long insertProduct() {
        return productDao.insert(new Product(new Name("테스트"), "http://test", new Price(1000)));
    }

    private Long insertMember() {
        return memberDao.insert(new Member(1L, new Email("test@wooteco.com"), new Password("test")));
    }

    @Test
    @DisplayName("save 테스트")
    void saveWhenCreate() {
        // given
        final CartService cartService = new CartService(cartDao, productDao);
        final Long productId = insertProduct();
        final Long memberId = insertMember();
        // when
        final RequestCartDto requestCartDto = new RequestCartDto(productId);
        cartService.save(memberId, requestCartDto);
        // then
        final Cart cart = cartDao.findByMemberIdAndProductId(memberId, productId).orElseThrow();
        assertEquals(cart.getMemberId(), memberId);
        assertEquals(cart.getProductId(), productId);
        assertEquals(cart.getQuantity().getValue(), 1);
    }

    @Test
    @DisplayName("같은 멤버가 같은 상품을 추가할 수 있다.")
    void saveWhenUpdate() {
        // given
        final CartService cartService = new CartService(cartDao, productDao);
        final Long productId = insertProduct();
        final Long memberId = insertMember();
        final RequestCartDto requestCartDto = new RequestCartDto(productId);
        // when
        cartService.save(memberId, requestCartDto);
        cartService.save(memberId, requestCartDto);
        cartService.save(memberId, requestCartDto);
        // then
        final Cart cart = cartDao.findByMemberIdAndProductId(memberId, productId).orElseThrow();
        assertEquals(cart.getMemberId(), memberId);
        assertEquals(cart.getProductId(), productId);
        assertEquals(cart.getQuantity().getValue(), 3);
    }

    @Test
    @DisplayName("display 테스트")
    void display() {
        // given
        final CartService cartService = new CartService(cartDao, productDao);
        final Long productId = insertProduct();
        final Long memberId = insertMember();
        final RequestCartDto requestCartDto = new RequestCartDto(productId);
        cartService.save(memberId, requestCartDto);
        // when
        final List<ResponseCartDto> responseCartDtos = cartService.display(memberId);
        // then
        assertEquals(responseCartDtos.get(0).getProductId(), productId);
        assertEquals(responseCartDtos.get(0).getProductName(), "테스트");
        assertEquals(responseCartDtos.get(0).getProductImage(), "http://test");
        assertEquals(responseCartDtos.get(0).getProductPrice(), 1000);
        assertEquals(responseCartDtos.get(0).getQuantity(), 1);
    }

    @Test
    @DisplayName("delete 테스트")
    void delete() {
        // given
        final CartService cartService = new CartService(cartDao, productDao);
        final Long productId = insertProduct();
        final Long memberId = insertMember();
        final RequestCartDto requestCartDto = new RequestCartDto(productId);
        cartService.save(memberId, requestCartDto);
        // when
        cartService.delete(memberId, productId);
        // then
        final Cart cart = cartDao.findByMemberIdAndProductId(memberId, productId).orElse(null);
        assertNull(cart);
    }
}
